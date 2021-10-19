package uz.english.db.algebra

import cats.effect.kernel.Async
import cats.implicits
import skunk.Session

trait WordAlgebra[F[_]]:


object WordAlgebra:
  def apply[F[_] : Async](session: Session[F]) = new WordAlgebra[F]