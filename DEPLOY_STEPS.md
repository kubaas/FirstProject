# 🚀 DEPLOYMENT NA RENDER.COM - KROK PO KROKU

## ✅ Co będziesz mieć:
- ✅ Frontend (Angular) - dostępny 24/7 online
- ✅ Backend (Spring Boot) - dostępny 24/7 online
- ✅ Baza PostgreSQL - darmowa na Neon.tech
- ✅ **100% DARMOWE** - żaden koszt!

---

## 📝 INSTRUKCJA (10 minut)

### KROK 1: Utwórz konto GitHub (jeśli nie masz)
- Przejdź na https://github.com/signup
- Utwórz bezpłatne konto

### KROK 2: Push kodu do GitHub

```bash
cd /Users/jakubsmus/Programing/FirstProject

# Jeśli to Twój pierwszy raz:
git init
git config user.email "twoj@email.com"
git config user.name "Twoje Imię"

git add .
git commit -m "Initial commit - IMO app"

# Utwórz repo na GitHub i dodaj link
git remote add origin https://github.com/TWOJA_NAZWA/FirstProject.git
git branch -M main
git push -u origin main
```

### KROK 3: Utwórz FREE bazę danych na Neon.tech

1. Przejdź na https://neon.tech
2. Kliknij "Sign Up" → zaloguj się GitHub
3. Utwórz "New Project"
4. Region: "Europe (Poland)" lub bliski Tobie
5. Kliknij na projekt
6. W lewym menu: "Connection string"
7. Skopiuj **Pooled connection** (wygląda tak):
```
postgresql://user:password@host/database
```

---

### KROK 4: Utwórz backend na Render.com

1. Przejdź na https://render.com
2. Kliknij "Sign up" → zaloguj się GitHub
3. Kliknij **"New +"** → **"Web Service"**
4. Wybierz swoje repo FirstProject
5. Ustawienia:
   - **Name**: `imo-backend`
   - **Region**: Frankfurt (EU)
   - **Branch**: main
   - **Runtime**: Docker
   - **Build Command**: `mvn clean package -DskipTests -q`
   - **Start Command**: `java -jar target/imo-backend-*.jar`

6. Kliknij **"Create Web Service"** (czekaj 10 minut na build)

### KROK 5: Dodaj zmienne środowiskowe do backendu

Po ukończeniu builda (po ~10 minutach):

1. W panelu Render Backend: kliknij **"Environment"**
2. Kliknij **"Add Environment Variable"**
3. Dodaj zmienne (jedna po drugiej):

```
PORT = 8080
SPRING_DATASOURCE_URL = postgresql://user:password@host/database
SPRING_DATASOURCE_USERNAME = user
SPRING_DATASOURCE_PASSWORD = password
SPRING_JPA_HIBERNATE_DDL_AUTO = update
SPRING_JPA_DATABASE_PLATFORM = org.hibernate.dialect.PostgreSQLDialect
```

(Wstaw swój connection string z Neon!)

4. Kliknij **"Save"** - Backend się przebuduje

### KROK 6: Utwórz frontend na Render.com

1. Kliknij **"New +"** → **"Web Service"**
2. Wybierz repo FirstProject
3. Ustawienia:
   - **Name**: `imo-frontend`
   - **Region**: Frankfurt (EU)
   - **Branch**: main
   - **Runtime**: Docker
   - **Build Command**: `npm install && npm run build`
   - **Start Command**: `nginx -g 'daemon off;'`

4. Kliknij **"Create Web Service"** (czekaj ~5 minut)

### KROK 7: Sprawdź czy wszystko działa

Po ~15 minutach powinny być oba serwisy online:

1. **Backend**: https://imo-backend.onrender.com/api/health
   - Powinno wyświetlić: `{"status":"ok"}`

2. **Frontend**: https://imo-frontend.onrender.com
   - Powinna się załadować aplikacja

---

## 🎉 GOTOWE!

Twoja aplikacja jest online!

**Link do wysłania znajomym**: https://imo-frontend.onrender.com

---

## ⚠️ Ważne rzeczy do wiedzy

### Backend usypia się po 15 minutach braku aktywności
Jeśli nikt nie używa aplikacji 15 minut, backend usypia się.
Gdy ktoś wejdzie - budzi się (czeka ~30 sekund).

**Rozwiązanie**: Aplikacja sama ping'a backend co 10 minut, więc będzie zawsze gotowy.

### Gdzie znaleźć logi?
1. Render Dashboard → kliknij serwis (Backend lub Frontend)
2. "Logs" tab

### Jak zaktualizować kod?
```bash
git add .
git commit -m "Moje zmiany"
git push origin main
```
Render automatycznie przebuduje!

---

## 💾 Konto Neon.tech

Connection string: Skopiuj z panelu https://console.neon.tech/app/projects

Zawiera: 
- Username (default: `neondb_owner`)
- Password (losowe)
- Host
- Database name

---

## 🚨 Jeśli coś nie działa

1. **Sprawdź logi backendu** (Render → Backend → Logs)
2. **Sprawdź connection string** do bazy (czy jest poprawny)
3. **Czy repo ma Dockerfile?** (powinien być w `backend/` i `frontend/`)
4. **Branch to main?** (nie master!)

---

## 📱 Czego można używać

- ✅ Render: 750 godzin dyno/miesiąc (wystarczy na zawsze!)
- ✅ Neon: 3 branches, free PostgreSQL (zawsze darmowa!)
- ✅ GitHub: nieograniczone publiczne repo

---

**Powodzenia! 🚀**
