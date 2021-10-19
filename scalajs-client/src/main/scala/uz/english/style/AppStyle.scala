package uz.english.style

import scalacss.internal.Attrs.animationIterationCount.infinite
import scalacss.internal.Attrs.animationTimingFunction.linear
import scalacss.internal.{FontFace, Keyframes, NonEmptyVector}
import uz.english.CssSettings.*

import scala.language.postfixOps
import scala.concurrent.duration.{DurationDouble, DurationInt}

object AppStyle extends StyleSheet.Inline {
  import dsl._

  val frijole: FontFace[String] = fontFace("Frijole")(
    _.src("url(/assets/font/Frijole.ttf)")
     .fontStyle.normal
     .fontWeight._400
  )

  val from: StyleA = keyframe(
    transform := "rotate(0deg)"
  )

  val to: StyleA = keyframe(
    transform := "rotate(360deg)"
  )

  val rotateDiv: Keyframes = keyframes(
    (0%%) -> from,
    (100%%) -> to
  )

  val formShadowColor = c"#ffa580"

  val box: StyleA = style(
    background := c"#f8f4e5",
    padding(50px, 100px),
    border(2px, solid, rgba(0,0,0,1)),
    boxShadow := s"15px 15px 1px #ffa580, 15px 15px 1px 2px rgba(0,0,0,1)"
  )

  val button: StyleA = style (
    display.block,
    fontSize(14 pt),
    background := formShadowColor,
    lineHeight(28 px),
    margin(0 px, auto),
    transition := ".2s all ease-in-out",
    fontFamily(frijole),
    outline.none,
    border(1 px, solid, black),
    boxShadow := "3px 3px 1px #95a4ff, 3px 3px 1px 1px black",
    padding(0 px, 20 px),

    &.hover(
      backgroundColor(black),
      color(white),
      border(1 px, solid, black)
    ),
  )

  val input: StyleA = style(
    display.block,
    width(100%%),
    fontSize(14 pt),
    background := c"#f8f4e5",
    lineHeight(28 pt),
    marginBottom(28 pt),
    borderTop.none,
    borderLeft.none,
    borderRight.none,
    fontFamily(frijole),
    borderBottom(5 px, solid, rgb(117, 117, 117)),
    minWidth(250 px),
    paddingLeft(5 px),
    outline.none,
    color(rgb(117, 117, 117)),

    &.hover(
      borderBottom(5 px, solid, formShadowColor)
    ),
  )

  val circleBoxPosition: StyleA = style(
    content.string(""),
    position.absolute,
    top(0 px),
    left(0 px),
    width(100 %%),
    height(100 %%),
    zIndex(-1),
    animationName(rotateDiv),
    animationDuration(5 seconds),
    animationTimingFunction.linear,
    animationIterationCount.infinite
  )

  val circleBox: StyleA = style(
    position.relative,
    textAlign.center,
    width(280 px),
    height(300 px),
    borderRadius(74%%, 82%%, 70%%, 88%%),
    display.table,
    padding(20 px),
    backgroundColor(rgba(255, 165, 128, .9)),
    cursor.pointer,
    zIndex(1),
    transition := "0.5s",
    color(c"#227093"),

    &.before(
      circleBoxPosition,
      borderRadius(130%%, 151%%, 189%%, 166%%),
      backgroundColor(rgba(255,165,128,.7)),
      animationDelay(0 seconds),
      transition := "0.5s"
    ),
    &.after(
      circleBoxPosition,
      borderRadius(145%%, 86%%, 80%%, 90%%),
      backgroundColor(rgba(255,165,128,.3)),
      animationDelay(0.2 seconds),
      transition := "0.5s"
    ),
    unsafeChild("div")(
      display.tableCell,
      verticalAlign.middle,
      unsafeChild("h2")(
        fontSize(20 px)
      ),
      unsafeChild("p")(
        marginTop(20 px),
        fontSize(18 px)
      )
    ),
    &.hover(
      backgroundColor(rgba(9,113,195,.8)),
      color(white)
    ),
    &.hover.after(
      backgroundColor(rgba(9,113,195,.7))
    ),
    &.hover.before(
      backgroundColor(rgba(9,113,195,.3))
    )

  )
}
