# Block Stream Example

## Usage

1. Create an account and fill aergo using [Aergo Faucet](https://faucet.aergoscan.io/)
2. Fill account information in application.yml
   ```yaml
     account:
       from:
         address: AmLjs1v2URs1xSitMweA9nXqviK7ty1wF9SqACWniP8xNzHTEcSf
         wif: 47NKopnsGJrGDuGwVhZw1DCG4BQFRQ3JX7J8hSWwM43cvfDEHdcWa8TCBWEiixe6aTbdBHmaj
         password: 1234
       to:
         address: AmLjs1v2URs1xSitMweA9nXqviK7ty1wF9SqACWniP8xNzHTEcSf
   ```
3. Run server
4. Request with `http://localhost:9000/send`
