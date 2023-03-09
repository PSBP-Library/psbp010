package psbp.specification.materialization

private[psbp] trait MainProgramMaterialization[-A, >-->[-_, +_], +B]:

  private[psbp] lazy val `(u>-->u)=>(a?=>b)`: (Unit >--> Unit) => (A ?=> B)
