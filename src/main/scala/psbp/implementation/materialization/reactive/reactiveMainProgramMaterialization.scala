package psbp.implementation.materialization.reactive

import psbp.specification.materialization.{MainProgramMaterialization}

import psbp.implementation.computation.reactive.{Reactive, `=>R`}

private[psbp] given reactiveMainProgramMaterialization
    : MainProgramMaterialization[Unit, `=>R`, Unit] =
  new:
    private[psbp] override lazy val `(u>-->u)=>(a?=>b)`
        : (Unit `=>R` Unit) => (Unit ?=> Unit) = `u=>r[u]` =>
      `u=>r[u]`(summon[Unit])(u => u)
