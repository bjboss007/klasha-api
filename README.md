# KLASHA Country Cities Documentation

The API Controller handles requests related to country data, states data, exchange data, and cities data in the Klasha application.

## Endpoints

### Get Country Data

Retrieves country data based on the provided country name.

- **URL:** `/api/v1/klasha/countries`
- **HTTP Method:** GET

#### Request Parameters

- `country` (optional, default: Nigeria): The name of the country for which data is requested.

#### Response

```json
{
    "status": true,
    "code": 200,
    "message": "Country Data fetched!",
    "data": {
        "population": 195874740,
        "capital": "Abuja",
        "location": {
            "long": 8,
            "lat": 10
        },
        "currency": "NGN",
        "iso2": "NG",
        "iso3": "NGA"
    },
    "timestamp": "2023-06-18 23:28:29"
} 
```

### Get States Data

Retrieve states and their corresponding cities' data for a specific country.

- **URL:** `/api/v1/klasha/countries/states`
- **HTTP Method:** GET

#### Request Parameters

- `country` (optional, default: Nigeria): The name of the country for which states data is requested.

#### Response

```json

{
    "status": true,
    "code": 200,
    "message": "States fetched successfully",
    "data": [
        {
            "name": "Abia State",
            "cites": [
                "Aba",
                "Amaigbo",
                "Arochukwu",
                "Bende",
                "Ohafia-Ifigh",
                "Umuahia"
            ],
            "state_code": "AB"
        },
        {
            "name": "Adamawa State",
            "cites": [
                "Ganye",
                "Gombi",
                "Holma",
                "Jimeta",
                "Madagali",
                "Mayo-Belwa",
                "Mubi",
                "Ngurore",
                "Numan",
                "Toungo",
                "Yola"
            ],
            "state_code": "AD"
        },
        {
            "name": "Akwa Ibom State",
            "cites": [
                "Eket",
                "Esuk Oron",
                "Ikot Ekpene",
                "Itu",
                "Uyo"
            ],
            "state_code": "AK"
        }
    ],
    "timestamp": "2023-06-18 23:28:29"
}

```

### Get Exchange Data

Convert currency of a sourc Country to a target currency

- **URL:** `/api/v1/klasha/countries/exchange`
- **HTTP Method:** GET

#### Request Parameters

The request requires a query parameter with the following properties:

- `sourceCountry` (required): The source currency.
- `targetCurrency` (required): The target currency.
- `amount` (required): The amount to be converted.

#### Response

```json

{
    "status": true,
    "code": 200,
    "message": "Exchange successfully done",
    "data": {
        "sourceCurrency": "NGN",
        "targetCurrency": "EUR",
        "amount": "81.12"
    },
    "timestamp": "2023-06-19 17:30:44"
}
```

### Get Cities

Retrieves Cities with the highest population from three different countries `["Ghana", "Italy", "New Zealand"]`.

- **URL:** `/api/v1/klasha/countries/cities`
- **HTTP Method:** GET

#### Request Parameters

- `numberOfCities` (optional, default: 10): The number of cities for which population data is requested.

#### Response
```json
{
    "status": true,
    "code": 200,
    "message": "Fetched",
    "data": [
        {
            "city": "ROMA",
            "country": "Italy",
            "recentPopulation": {
                "value": 2626553.0,
                "year": 2012
            }
        },
        {
            "city": "ACCRA",
            "country": "Ghana",
            "recentPopulation": {
                "value": 1658937.0,
                "year": 2000
            }
        },
        {
            "city": "Auckland",
            "country": "New Zealand",
            "recentPopulation": {
                "value": 1529400.0,
                "year": 2013
            }
        },
        {
            "city": "Milano",
            "country": "Italy",
            "recentPopulation": {
                "value": 1251137.0,
                "year": 2012
            }
        },
        {
            "city": "Kumasi",
            "country": "Ghana",
            "recentPopulation": {
                "value": 1170270.0,
                "year": 2000
            }
        }
    ],
    "timestamp": "2023-06-19 20:54:35"
}
```

## Usage

To use the API Controller, send HTTP requests to the corresponding endpoints as described above.

```bash
# Example: Get country data
GET /api/v1/klasha/countries?country=Nigeria

# Example: Get states data
GET /api/v1/klasha/countries/states?country=Nigeria

# Example: Get exchange data
GET /api/v1/klasha/countries/exchange?sourceCountry=Nigeria&targetCurrency=EUR&amount=4000

# Example: Get cities data
GET /api/v1/klasha/countries/cities?numberOfCities=10