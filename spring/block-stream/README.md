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
4. Request with `http://localhost:9000/custom?txpersec=50&sec=1`


txpersec/sec/시도찻수 : 총 tx 수
시작 block No ~ : 몇개씩 있는지

50/1/1 : 50
30457050 ~ 30457053 : 15 , 27 , 5 , 3 / 첫 transaction 보내지고 block 2개째에 Tx 들어감

50/1/2 : 50
30457052 : 50 / 첫 transaction 보내지고 block 3개째에 Tx 들어감
 
50/10/1 : 500
30457325 ~ 30457342 : 27, 0, 67, 20, 22, 27, 1, 65, 19, 24, 28, 1, 67, 19, 24, 25, 1, 63 / 첫 transaction 보내지고 block 2개째에 Tx 들어감

50/10/2 : 500(327) / 351번째 에서 [the minimum required amount of gas: 10000] 에러남 / 328 부터는 tx 조회도 안됨 ( 8kDtgTGwoHkUMHGrdgU5aH9vBMJZniDCUWiFHnjHpkf9, 8XEMmEhfcw55mYCtvv7Asfn2feFbrC46AGSMguxpsnvP0)
30458048 ~ 30458060 : 3, 23, 28, 0, 68, 21, 22, 29, 0, 66, 21, 23, 23 / 첫 transaction 보내지고 block 1개째에 Tx 들어감