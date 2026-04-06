# Sistem za upravljanje događajima i lokacijama

**Projektni zadatak:** SVT i KVT  
**Student:** SR 27/2023

## O Projektu

Fullstack veb aplikacija za upravljanje događajima i lokacijama. Aplikacija omogućava korisnicima da kreiraju, pregledaju i upravljaju događajima, lakacijama, kao i ostavljaju recenzije. Administrators imaju pristup dodatnim funkcionalnostima za upravljanje sistemom i odobravanje zahteva za registraciju.

## Tehnologije

### Backend
- **Java** sa **Spring Boot** okvirom
- **Maven** za upravljanje zavisnostima
- RESTful API

### Frontend
- **Angular** (17+)
- **TypeScript**
- **SCSS** za stilizovanje
- Responsive dizajn

## Ključne Funkcionalnosti

### Za obične korisnike:
- ✅ Registracija i prijava
- ✅ Pregled liste događaja
- ✅ Kreiranje novih događaja
- ✅ Pregled lokacija
- ✅ Dodavanje i pregled recenzija

### Za administratore:
- ✅ Odobravanje zahteva za registraciju
- ✅ Upravljanje korisnicima
- ✅ Pregled svih događaja i lokacija
- ✅ Dashboard sa statistikom

## Struktura Projekta

```
/backend   - Java Spring Boot aplikacija (REST API)
/frontend  - Angular aplikacija (klijentska strana)
```

## Instalacija i Pokretanje

### Backend
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm start
```

## Kako Radi

1. Novi korisnik se registruje i čeka odobrenje administratora
2. Nakon odobrenja, korisnik se može prijaviti
3. Prijavljen korisnik može pregledariti događaje i lokacije
4. Korisnik može kreirati nove događaje i lokacije
5. Korisnici mogu ostavljati recenzije na događaje
6. Administrator upravlja sistemom iz admin panela
