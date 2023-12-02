# Scheduled Weather service
This service fetches weather data from [realtime weather API](https://www.weatherapi.com/docs/). Weather API key, request rate and database credentials can be configured externally via env variables.
## Environment variables
- DB_URL - used to set URL to the database instance
- DB_USER - used to specify database user
- DB_PASSWORD - used to specify database password
- WEATHER_API_KEY - used to specify API key
- WEATHER_API_REQUEST_RATE - used to specify request rate in cron format (e.g. ```0 0 * * * *``` will fetch data from external API at the top of every hour)
## Endpoints
### **GET** ```/latest```   
```200 OK``` with latest stored data:
``` json
{
    "id": 15,
    "tempCelsius": -4.0,
    "tempFahrenheit": 24.8,
    "windSpeedMs": 1.8888888888888888,
    "pressureMillibars": 1002.0,
    "humidity": 93,
    "conditionText": "Mist",
    "city": "Minsk",
    "requestedAt": "2023-12-01T17:08:40.556873"
}
```

```404 Not found``` when the database is empty:
``` json
{
    "message": "There is no weather data stored in db",
    "i18nKey": "error.weather.no.data"
}
```

### **POST** ```/average```
Request body:
``` json
{
    "from" : "2023-11-30T15:00:00",
    "to" : "2023-12-02T12:57:00"
}
```
Responses:  
```200 OK``` with average for given period rounded to two decimal points
```json
{
    "avgTempCelsius": -3.9,
    "avgTempFahrenheit": 24.9,
    "avgWindSpeedMs": 1.0,
    "avgPressureMillibars": 1009.0,
    "avgHumidity": 96.0
}
```

```404 Not found``` when there is no data for given period
```json
{
    "message": "There is no weather data between 2023-11-30T15:00 and 2023-12-02T12:57 stored in db",
    "i18nKey": "error.weather.no.data"
}
```

```400 Bad request``` when the end of the period is before the beginning, or any of the given dates are in future
```json
{
    "message": "The beginning date of the period (2024-11-30T15:00) should be before the ending date of the period (2023-12-02T13:38)",
    "i18nKey": "error.weather.incorrect.date"
}
```