package psbp.specification.materialization

import psbp.specification.program.{Program}

private[psbp] def materializeProgram[
    A,
    Z,
    >-->[-_, +_]
      : [>-->[-_, +_]] =>> Program[>-->, &&, ||]
      : [>-->[-_, +_]] =>> MainProgramMaterialization[A, >-->, B],
    &&[+_, +_],
    ||[+_, +_],
    Y,
    B
]: (Z >--> Y) => (Unit >--> Z) => ((Z && Y) >--> Unit) => (A ?=> B) = `z>-->y` =>
  `u>-->z` =>
    `(z&&y)=>u` =>

      lazy val summonedProgram = summon[Program[>-->, &&, ||]]

      import summonedProgram.{Let}

      lazy val summonedMainProgramMaterialization =
        summon[MainProgramMaterialization[A, >-->, B]]

      import summonedMainProgramMaterialization.{`(u>-->u)=>(a?=>b)`}

      `(u>-->u)=>(a?=>b)` { `u>-->z` >--> { Let { `z>-->y` } In { `(z&&y)=>u` } } }
