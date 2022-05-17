# MIKADO-playground
Mikado playground backend + frontend

## Backend of LIVE
- The MIKADO-playground folder contains the backend of the live environment.
- To run the backend services:
- mvn function:run -Drun.functionTarget=org.eclipse.epsilon.live.mikado.RunEvaluation -Drun.port=8001
- mvn -X function:run -Drun.functionTarget=org.eclipse.epsilon.live.mikado.Validator -Drun.port=8002
- mvn -X function:run -Drun.functionTarget=org.eclipse.epsilon.live.mikado.SC2HTML -Drun.port=8003