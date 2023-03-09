package psbp.implementation.materialization.active

import psbp.specification.materialization.{MainProgramMaterialization}

import psbp.implementation.computation.active.{Active, `=>A`}

private[psbp] given activeMainProgramMaterialization
    : MainProgramMaterialization[Unit, `=>A`, Unit] =
  new:
    private[psbp] override lazy val `(u>-->u)=>(a?=>b)`
        : (Unit `=>A` Unit) => (Unit ?=> Unit) = `u=>a[u]` => `u=>a[u]`(summon[Unit])
