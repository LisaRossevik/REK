REK
ParkingApp - Programmerings oppgave java utviklere
MoonPark har gitt deg i oppdrag å utvikle en webservice som kalkulerer prisen for en parkering i deres P-Hus.
Prisen for en parkering er gitt via takstssoner. I dag finnes det tre takstssoner: M1, M2 og M3.
Det er forventet at antallet takstsoner vil vokse raskt i nærmeste fremtid.

OPPGAVE1 : Skriv kode som beregner takst for en parkering i takstsone M1.
Takst for parkering i M1 beregenes som følger:
60 kr timen, beregnes for hvert påbegynte minutt.
Å definere inn og ut verdiene er en del av oppgaven. Det samme gjelder tester.

SVAR : Se koden. Klasse ParkingBillM1.java


OPPGAVE 2: Skriv kode som beregner takst for en parkering i takstsone M2 (takstsonoe tas her som innput til rutinen).
Takst for parkering i M2 beregnes som følger:
100 kr pr påbegynte time på hverdager
I helgen er prisen det dobbelte, altså 299 kr pr påbegynte time.

SVAR : Se kode. Klasse ParkingBillM2.java


OPPGAVE 3: Eksponer koden fra oppgave 2 som en webtjeneste.
Klienter skal kunne gjøre en GET til stien /takst med nødvendige innput- parametre som en del av query strengen.
Responsen skal være en JSON-struktur.

SVAR: Se kode og prøv ut MoonPark-0.0.1-SNAPSHOT.jar.original
http://localhost:8080/takst?zone=M1&totalMinParked=181 => gir response:
{"totalMinParked":181,"parkingFeePrHour":60,"weekDay":null,"sum":240}
http://localhost:8080/takst?zone=M2&totalMinParked=181&weekDay=sunday => gir response:
{"totalMinParked":181,"parkingFeePrHour":200,"weekDay":"sunday","sum":800}
http://localhost:8080/takst?zone=M2&totalMinParked=181&weekDay=tuesday => gir response:
{"totalMinParked":181,"parkingFeePrHour":100,"weekDay":"tuesday","sum":400}


OPPGAVE 4: Utvid takstmotoren med støtte for takstsone M3:
Takst for parkerning i M3 beregnes som følger:
Mandag til lørdag mellom 08:00 og 16:00 er de første timene gratis, deretter koster det 3 kr pr påbegynte minutt
Mandag til lørdag utenom disse tidspunktene koster det 3 kr pr min.
Søndager er parkering gratis

SVAR: Se kode og prøv ut MoonPark-0.0.1-SNAPSHOT.jar.original
http://localhost:8080/takst?zone=M3&start=2020-11-10T00:00:00&end=2020-11-10T01:00:00 => gir respons: 
{"totalMinParked":60,"parkingFeePrHour":0,"weekDay":"tuesday","sum":180,"minWeekEvening":0,"minWeekDay":0,"minWeekMorning":60,"minSunday":0,"startTime":"2020-11-10T00:00:00","endTime":"2020-11-10T01:00:00","midnight":"2020-11-10T00:00:00","dayStart":"2020-11-10T08:00:00","eveningStart":"2020-11-10T16:00:00"}
http://localhost:8080/takst?zone=M3&start=2020-11-14T12:00:00&end=2020-11-14T13:00:00 => gir response: 
{"totalMinParked":60,"parkingFeePrHour":0,"weekDay":"saturday","sum":0,"minWeekEvening":0,"minWeekDay":0,"minWeekMorning":0,"minSunday":0,"startTime":"2020-11-14T12:00:00","endTime":"2020-11-14T13:00:00","midnight":"2020-11-14T00:00:00","dayStart":"2020-11-14T08:00:00","eveningStart":"2020-11-14T16:00:00"}
http://localhost:8080/takst?zone=M3&start=2020-11-14T12:00:00&end=2020-11-14T15:00:00 => gir responsen:
{"totalMinParked":180,"parkingFeePrHour":0,"weekDay":"saturday","sum":240,"minWeekEvening":0,"minWeekDay":120,"minWeekMorning":0,"minSunday":0,"startTime":"2020-11-14T12:00:00","endTime":"2020-11-14T15:00:00","midnight":"2020-11-14T00:00:00","dayStart":"2020-11-14T08:00:00","eveningStart":"2020-11-14T16:00:00"}
(trekker fra 60 min på minWeekDay siden den første timen mellom kl 8 og 16 på lørdager er gratis)
http://localhost:8080/takst?zone=M3&start=2020-11-15T12:00:00&end=2020-11-15T15:00:00 => gir responsen: 
{"totalMinParked":180,"parkingFeePrHour":0,"weekDay":"sunday","sum":0,"minWeekEvening":0,"minWeekDay":0,"minWeekMorning":0,"minSunday":180,"startTime":"2020-11-15T12:00:00","endTime":"2020-11-15T15:00:00","midnight":"2020-11-15T00:00:00","dayStart":"2020-11-15T08:00:00","eveningStart":"2020-11-15T16:00:00"}
http://localhost:8080/takst?zone=M3&start=2020-11-16T21:00:00&end=2020-11-16T23:00:00 => gir responsen: 
{"totalMinParked":120,"parkingFeePrHour":0,"weekDay":"monday","sum":360,"minWeekEvening":120,"minWeekDay":0,"minWeekMorning":0,"minSunday":0,"startTime":"2020-11-16T21:00:00","endTime":"2020-11-16T23:00:00","midnight":"2020-11-16T00:00:00","dayStart":"2020-11-16T08:00:00","eveningStart":"2020-11-16T16:00:00"}


Oppgave 5: MoonPark ønsker i utgangspunktet at priskalulatoren deres skal være offentlig tilgjengelig.
Dette viser seg å være svært populært og flere tredejepartstjenester tar den raskt i bruk.
Dessverre viser det seg at enkelte klienter av ulike grunner gjør ekstremt mange kall, noe som til tider
fører til at tjenesten blir overbelastet. Foreslå en strategi for å løse dette problemet.
Begrunn svaret.

SVAR:

Foreslår å legge på registrering /autentisering av klienter, hvor klientene mottar en api key de må bruke i kallet sitt.
Dermed kan vi registrere hvor mange kall hver enkelt klient gjør.
Vi kan legge på et maks antall kall pr minutt for hver klient.
Vi kan cashe ofte brukte data på server siden ved hjelp av DOM storage.

Vi kan kontakte klientene og hjelpe dem med å optimalisere koden sin:
Se om det de gjør requestes på data de ikke bruker i applikasjonen sin?
Se om nedlastede data sendt tilbake uten å ha blitt endret på?
Vi kan oppfordre til "sideloading" av data slik at de får to sett med data i en request.
Vi kan oppfordre dem til å bruke bulk og batch endepunkt, slik som Update Many Tickets.
Oppfordre til chasching av data på klient side.

Jeg ville forslått strategi nr 1 over, da det er enklest for oss å implementere uten å involvere klientene.
