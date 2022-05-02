# Back-End


To run the back-end locally too, you should clone this repo, run the following commands 

- `mvn function:run -Drun.functionTarget=org.eclipse.epsilon.live.mikado.RunEvaluation -Drun.port=8001`
- `mvn -X function:run -Drun.functionTarget=org.eclipse.epsilon.live.mikado.Flexmi2HTML -Drun.port=8002`

and update the frontend's `backend.json` as follows to make the front-end call the local instances of the respective web services.

```json
{
  "services": [
    {"name": "RunEvaluation","url": "http://localhost:8001/services/RunEvaluation", "port": "8001"},
    {"name": "Flexmi2HTML", "url": "http://localhost:8002/services/Flexmi2HTML","port": "8002"}
  ]
}
```