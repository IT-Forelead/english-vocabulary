package uz.english

import japgolly.scalajs.react.ScalaComponent.{BackendScope, Component}
import japgolly.scalajs.react.callback.Callback$package.Callback
import japgolly.scalajs.react.facade.SyntheticEvent
import org.scalajs.dom.raw.{HTMLInputElement, HTMLTextAreaElement}
import japgolly.scalajs.react.vdom.html_<^.*
import japgolly.scalajs.react.vdom.all.VdomTagOf
import japgolly.scalajs.react.{CtorType, ScalaComponent}
import org.scalajs.dom.document
import org.scalajs.dom.html.Div
import org.scalajs.dom.raw.HTMLInputElement
import scalacss.ScalaCssReact.*
import uz.english.style.AppStyle
import uz.english.style.AppStyle.*
import uz.english.CssSettings.*
import scalacss.toStyleSheetInlineJsOps
import uz.english.Username._
import uz.english.WordWithoutId._


object Main extends App with AjaxImplicits {

  case class State(username: UsernameType = "", word: WordType = "", definition: DefinitionType = "")
  type AppComponent = Component[Unit, State, Backend, CtorType.Nullary]

  class Backend($ : BackendScope[Unit, State]) {

    def onClickCreate(implicit state: State): Callback =
      if (state.username.isEmpty)
        Callback.alert("Please enter your name!")
      else
        post[Username]("/user", Username(state.username))
          .fail(onError)
          .done[String] { result =>
            $.setState(State()) >> Callback.alert(result)
          }.asCallback

    def addNewWord(implicit state: State): Callback =
      if (state.word.isEmpty)
        Callback.alert("Please enter word!")
      else if (state.definition.isEmpty)
        Callback.alert("Please enter definition of word!")
      else
        post[WordWithoutId]("/words", WordWithoutId(state.word, state.definition))
          .fail(onError)
          .done[String] { result =>
            $.setState(State()) >> Callback.alert(result)
          }.asCallback

    def onChangeUserName(e: SyntheticEvent[HTMLInputElement]): Callback =
      $.modState(_.copy(username = e.target.value))

    def onChangeWord(e: SyntheticEvent[HTMLInputElement]): Callback =
      $.modState(_.copy(word = e.target.value))

    def onChangeDefinition(e: SyntheticEvent[HTMLTextAreaElement]): Callback =
      $.modState(_.copy(definition = e.target.value))

    def createUserForm(implicit state: State): VdomTagOf[Div] =
      <.div(box)(
        <.input(^.placeholder := "Name...", ^.onChange ==> onChangeUserName, ^.value := state.username, input),
        <.button(button, ^.onClick --> onClickCreate)("Create")
      )

    def createWordForm(implicit state: State): VdomTagOf[Div] =
      <.div(box)(
        <.input(^.placeholder := "New word...", ^.onChange ==> onChangeWord, ^.value := state.word, input),
        <.textarea(^.placeholder := "Definition", ^.onChange ==> onChangeDefinition, ^.value := state.definition, input),
        <.button(button, ^.onClick --> addNewWord)("Add Word")
      )

    def animationBox(implicit state: State): VdomTagOf[Div] =
      <.div(circleBox)(
        <.div(
          <.h2("This Is Title Article"),
          <.p("Lorem Ipsum")
        )
      )

    def render(implicit state: State): VdomTagOf[Div] =
      <.div(createUserForm, createWordForm)
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
