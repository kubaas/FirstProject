# 🚀 DEPLOYMENT - OSTATNIE 3 KROKI (10 MINUT W PRZEGLĄDARCE)

## ✅ JUŻ GOTOWE:
- ✅ Kod pushowany na GitHub: https://github.com/kubaas/FirstProject
- ✅ Wszystkie Dockerfiles
- ✅ Environment variables skonfigurowane

---

## 📝 CO MUSISZ ZROBIĆ (tylko 3 rzeczy, każde zajmie 2-3 minuty):

### KROK 1: Utwórz FREE bazę danych na Neon.tech (2 minuty)

1. Przejdź na https://neon.tech
2. Kliknij **"Sign Up"** → zaloguj się GitHub (najprościej)
3. Potwierdź: **"Authorize neondb"**
4. Kliknij **"New Project"**
5. Region: **Europe** (wybiersz Region blisko Polski, np. Frankfurt)
6. Czekaj ~30 sekund, aż projekt się utworzy
7. **SKOPIUJ CONNECTION STRING**:
   - Kliknij: **"Connection string"**
   - Wybierz: **"Pooled connection"** (ważne!)
   - Skopiuj cały string (zaczyna się od: `postgresql://...`)
   
   **Zachowaj ten string - będziesz go potrzebować za chwilę!**

---

### KROK 2: Utwórz Backend na Render.com (3 minuty)

1. Przejdź na https://render.com
2. Kliknij **"Sign Up"** → zaloguj się GitHub
3. Potwierdź: **"Authorize Render"**
4. Kliknij **"New +"** → **"Web Service"**
5. Połącz GitHub:
   - Kliknij: **"Connect account"**
   - Wybierz: **FirstProject**
   - Kliknij: **"Connect"**
6. Ustawienia Web Service:
   ```
   Name: imo-backend
   Region: Frankfurt (EU)
   Branch: main
   Runtime: Docker
   ```
7. Kliknij **"Create Web Service"**
8. **CZEKAJ 10-15 MINUT** na build (zobaczysz logi, czekaj aż pojawi się "Active")

---

### KROK 3: Dodaj zmienne do Backendu i Utwórz Frontend (3 minuty)

#### Część A: Zmienne do Backend (1 minuta)

Kiedy backend się zbudzuje (status = "Active"):

1. Kliknij na backend service
2. Idź do: **Environment** (w lewym menu)
3. Kliknij: **"Add Environment Variable"**
4. Dodaj zmienne (jedna po drugiej - kopiuj dokładnie):

   **Zmienna 1:**
   ```
   Key: SPRING_DATASOURCE_URL
   Value: [WKLEJ CONNECTION STRING Z NEON - cały ciąg postgresql://...]
   ```
   Kliknij Add

   **Zmienna 2:**
   ```
   Key: PORT
   Value: 8080
   ```
   Kliknij Add

5. Scroll w dół, kliknij: **"Save"**
6. Backend się przebuduje (czekaj ~2 minuty)

#### Część B: Utwórz Frontend (2 minuty)

1. Idź do głównego panelu Render (Home)
2. Kliknij: **"New +"** → **"Web Service"**
3. Połącz ten sam GitHub (FirstProject)
4. Ustawienia:
   ```
   Name: imo-frontend
   Region: Frankfurt (EU)
   Branch: main
   Runtime: Docker
   ```
5. Kliknij: **"Create Web Service"**
6. Czekaj ~10 minut na build

---

## 🎉 GOTOWE!

Po ~20 minutach będziesz mieć:

- **Frontend**: `https://imo-frontend.onrender.com` ← TEN LINK WYSYŁASZ ZNAJOMYM!
- **Backend**: `https://imo-backend.onrender.com/api/health` ← Automatycznie działa

---

## ✅ SPRAWDZENIE

Kiedy oba są "Active" (zielony status w Render):

1. Otwórz: https://imo-backend.onrender.com/api/health
   - Powinno wyświetlić: `{"status":"ok"}`

2. Otwórz: https://imo-frontend.onrender.com
   - Powinna się załadować aplikacja

3. Test aplikacji:
   - Wpisz dane w formularzu onboarding
   - Powinno się załadować plan posiłków

---

## 🎁 Co masz:

- ✅ Aplikacja dostępna 24/7
- ✅ Baza danych PostgreSQL (darmowa)
- ✅ Frontend i Backend (darmowe na zawsze!)
- ✅ **0 złotych/miesiąc**

---

## ⚠️ Wskazówki

1. **Jeśli backend się nie buduje**:
   - Sprawdź "Logs" w Render (kliknij serwis → Logs)
   - Najczęstszy problem: zły connection string do bazy

2. **Jeśli frontend nie łączy się z backendem**:
   - Otwórz Developer Tools (F12)
   - Sprawdź "Console" czy są błędy CORS
   - Sprawdź czy backend ma status "Active"

3. **Backend się czasem "uśpia"**:
   - To normalne - darmowy tier
   - Aplikacja sama go ping'uje co 10 minut
   - Gdy ktoś wejdzie - budzi się w ~30 sekund

---

**Powodzenia! 🚀 Napisz jeśli coś nie zadziała!**
