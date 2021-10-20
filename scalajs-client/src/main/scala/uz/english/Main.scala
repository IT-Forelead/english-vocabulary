package uz.english

import japgolly.scalajs.react.callback.{Callback, CallbackOption, CallbackTo}
import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.component.ScalaFn.Component
import japgolly.scalajs.react.internal.ReactCallbackExtensions.CallbackOptionObjExt.*
import japgolly.scalajs.react.facade.SyntheticEvent
import japgolly.scalajs.react.internal.CoreGeneral.{ReactKeyboardEvent, ScalaFnComponent}
import japgolly.scalajs.react.{CtorType, ScalaComponent, *}
import japgolly.scalajs.react.vdom.all.VdomTagOf
import japgolly.scalajs.react.vdom.html_<^.*
import org.scalajs.dom.{document, html}
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Div
import org.scalajs.dom.raw.{HTMLInputElement, HTMLTextAreaElement}
import scalacss.ScalaCssReact.*
import scalacss.toStyleSheetInlineJsOps
import uz.english.CssSettings.*
import uz.english.Username.*
import uz.english.WordWithoutId.*
import uz.english.style.AppStyle
import uz.english.style.AppStyle.*

import scala.concurrent.duration.DurationInt
import scala.scalajs.js.timers.setTimeout
import scala.util.Random

object Main extends App with AjaxImplicits {
  sealed trait Page
  case object Home extends Page
  case object User extends Page
  case object Word extends Page

  case class State(
    username: UsernameType = "",
    word: WordType = "",
    definition: DefinitionType = "",
    page: Page = Home,
    words: List[Word] = Nil,
    users: List[User] = Nil,
    started: Boolean = false,
    queueNumber: Int = 0,
    usersAndWords: List[UserAndWord] = Nil
  )
  type AppComponent = Scala.Component[Unit, State, Backend, CtorType.Nullary]

  def animationBox(userAndWord: UserAndWord): VdomTagOf[Div] =
    <.div(circleBox)(<.div(<.h2(userAndWord.user.name), <.p(userAndWord.word.value)))

  val RandomWords =
    ScalaFnComponent[List[UserAndWord]] { userAndWords =>
      <.div(grid)(userAndWords map animationBox: _*)
    }

  class Backend($ : Scala.BackendScope[Unit, State]) {

    def onClickCreate(implicit state: State): Callback =
      if (state.username.isEmpty)
        Callback.alert("Please enter your name!")
      else
        post[Username]("/user", Username(state.username))
          .fail(onError)
          .done[Result] { result =>
            $.modState(_.copy(username = "")) >> Callback.alert(result.text)
          }.asCallback

    def addNewWord(implicit state: State): Callback =
      if (state.word.isEmpty)
        Callback.alert("Please enter word!")
      else if (state.definition.isEmpty)
        Callback.alert("Please enter definition of word!")
      else
        post[WordWithoutId]("/words", WordWithoutId(state.word, state.definition))
          .fail(onError)
          .done[Result] { result =>
            $.modState(_.copy(word = "", definition = "")) >> Callback.alert(result.text)
          }.asCallback

    def getAllWords: Callback =
      get("/words")
        .fail(onError)
        .done[List[Word]] { words =>
          $.modState(_.copy(words = words))
        }.asCallback

    def getAllUsers: Callback =
      get("/user")
        .fail(onError)
        .done[List[User]] { users =>
          $.modState(_.copy(users = users), genAnimationBox($.withEffectsImpure.state))
        }.asCallback

    def genAnimationBox(implicit state: State): Callback = {
      val userAndWord = UserAndWord(
        state.users(state.queueNumber),
        state.words(Random.nextInt(state.words.length))
      )
      $.modState(_.copy(usersAndWords = userAndWord +: state.usersAndWords))
    }

    def onChangeUserName(e: SyntheticEvent[HTMLInputElement]): Callback =
      $.modState(_.copy(username = e.target.value))

    def onChangeWord(e: SyntheticEvent[HTMLInputElement]): Callback =
      $.modState(_.copy(word = e.target.value))

    def onChangeDefinition(e: SyntheticEvent[HTMLTextAreaElement]): Callback =
      $.modState(_.copy(definition = e.target.value))

    def onChangePage(selectedPage: Page)(implicit state: State): Callback =
      $.modState(_.copy(page = selectedPage))

    def onClickStart(implicit state: State): Callback =
      $.modState(_.copy(started = true)) >> getAllWords >> getAllUsers

    def createUserForm(implicit state: State): TagMod =
      <.div(box)(
        <.input(^.placeholder := "Name...", ^.onChange ==> onChangeUserName, ^.value := state.username, input),
        <.button(button, ^.onClick --> onClickCreate)("Create")).when(state.page == User)

    def createWordForm(implicit state: State): TagMod =
      <.div(box)(
        <.input(^.placeholder := "New word...", ^.onChange ==> onChangeWord, ^.value := state.word, input),
        <.textarea(
          ^.placeholder := "Definition",
          ^.onChange ==> onChangeDefinition,
          ^.value := state.definition,
          input),
        <.button(button, ^.onClick --> addNewWord)("Add Word")).when(state.page == Word)

    def navbar(implicit state: State): TagMod =
      <.div(navbarS)(
        <.ul(
          <.li(navActive.when(state.page == Home), ^.onClick --> onChangePage(Home))("Home"),
          <.li(navActive.when(state.page == User), ^.onClick --> onChangePage(User))("User"),
          <.li(navActive.when(state.page == Word), ^.onClick --> onChangePage(Word))("Word")))

    def nextUser(implicit state: State): Callback =
      $.modState(_.copy(queueNumber = state.queueNumber + 1)).when_(state.queueNumber < state.users.length) >>
        genAnimationBox

    def nextRound(implicit state: State): Callback =
      $.modState(_.copy(queueNumber = 0)).when_(state.queueNumber == state.users.length)

    def handleKey(e: ReactKeyboardEvent)(implicit state: State): Callback = {
      println(state.queueNumber)
      e.nativeEvent.keyCode match {
        case KeyCode.Space => nextUser
        case KeyCode.Enter => nextRound
        case _ => Callback.empty
      }
    }

    def homePage(implicit state: State): VdomTagOf[Div] =
      <.div(
        <.button(button, ^.cls := "d-block", ^.onClick --> onClickStart)("Start").when(state.page == Home && !state.started),
        <.div(grid, ^.onKeyPress ==> handleKey)(state.usersAndWords map animationBox: _*))

    def render(implicit state: State): VdomTagOf[Div] =
      <.div(navbar, homePage, createUserForm, createWordForm)
  }

  val App: AppComponent =
    ScalaComponent
      .builder[Unit]
      .initialState(State())
      .renderBackend[Backend]
      .build

  AppStyle.addToDocument()
  App().renderIntoDOM(document.getElementById("app"))

}
