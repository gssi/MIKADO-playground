# MIKADO-playground
Mikado playground backend + frontend

## Backend of LIVE
- The MIKADO-playground folder contains the backend of the live environment.
- To run the backend services:
- mvn function:run -Drun.functionTarget=org.eclipse.epsilon.live.mikado.RunEvaluation -Drun.port=8001
- mvn -X function:run -Drun.functionTarget=org.eclipse.epsilon.live.mikado.Flexmi2HTML -Drun.port=8002

## Frontend
- The mkdocs folder contains the frontend and the minimal MIKADO website. To run it:
- run ./serve.sh and visit the website.
