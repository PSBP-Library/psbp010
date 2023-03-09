# Program Specification Based Programming

This is lesson 010 of a "Program Specification Based Programming" course.

All comments are welcome at luc.duponcheel[at]gmail.com.

## Introduction

In lesson009 we have implemented `ParallelComposition` in terms of type `Reactive`, function level `Product`, and
function level `Sum` using the `Akka` actor library.

## Content

We have specified `Program` in terms of `Function`, `DataStructure` and `Algorithm`.

`Function` is used for lifting primitive functions to programs.

`DataStructure` is specified in terms of `Product` and `Sum`.

`Algorithm` is specified in terms of `IfThenElse`, `LocalDefinition`,`SequentialComposition` and `ParallelComposition`.

`Product` is used for composing and decomposing data.

`Sum` is used for defining if-then-else logic.

### Main program materialization

A main program is a program of type `Unit >--> Unit`.

Below the main program materialization related  program concept is specified.

```scala
package psbp.specification.materialization

private[psbp] trait MainProgramMaterialization[-A, >-->[-_, +_], +B]:

  private[psbp] lazy val `(u>-->u)=>(a?=>b)`: (Unit >--> Unit) => (A ?=> B)
```

### Program materialization

Below corresponding program materialization is defined.

```scala
package psbp.specification.materialization

import psbp.specification.program.{Program}

private[psbp] def materialize[
    A,
    Z,
    >-->[-_, +_]
      : [>-->[-_, +_]] =>> Program[>-->, &&, ||]
      : [>-->[-_, +_]] =>> MainProgramMaterialization[A, >-->, B],
    &&[+_, +_],
    ||[+_, +_],
    Y,
    B
]: (Unit >--> Z) => (Z >--> Y) => ((Z && Y) >--> Unit) => (A ?=> B) = `u>-->z` =>
  `z>-->y` =>
    `(z&&y)=>u` =>

      lazy val summonedProgram = summon[Program[>-->, &&, ||]]

      import summonedProgram.{Let}

      lazy val summonedMainProgramMaterialization =
        summon[MainProgramMaterialization[A, >-->, B]]

      import summonedMainProgramMaterialization.{`(u>-->u)=>(a?=>b)`}

      `(u>-->u)=>(a?=>b)` { `u>-->z` >--> { Let { `z>-->y` } In { `(z&&y)=>u` } } }
```

For now a program is transformed to a main program using a program of type `Unit >--> Z` and a program of type
`(Z && Y) >--> Unit`.

### Implementing `MainProgramMaterialization` in terms of type `Active`

```scala
package psbp.implementation.materialization.active

import psbp.specification.materialization.{MainProgramMaterialization}

import psbp.implementation.computation.active.{Active, `=>A`}

private[psbp] given activeMainProgramMaterialization
    : MainProgramMaterialization[Unit, `=>A`, Unit] =
  new:
    private[psbp] override lazy val `(u>-->u)=>(a?=>b)`
        : (Unit `=>A` Unit) => (Unit ?=> Unit) = `u=>a[u]` => `u=>a[u]`(summon[Unit])
```

### Implementing `MainProgramMaterialization` in terms of type `Reactive`

```scala
package psbp.implementation.materialization.reactive

import psbp.specification.materialization.{MainProgramMaterialization}

import psbp.implementation.computation.reactive.{Reactive, `=>R`}

private[psbp] given reactiveMainProgramMaterialization
    : MainProgramMaterialization[Unit, `=>R`, Unit] =
  new:
    private[psbp] override lazy val `(u>-->u)=>(a?=>b)`
        : (Unit `=>R` Unit) => (Unit ?=> Unit) = `u=>r[u]` =>
      `u=>r[u]`(summon[Unit])(u => u)
```

## Conclusion

We have specified main program materialization, defined corresponding program materialization and implemented main
program materialization in terms of type `Active` and type `Reactive`.
