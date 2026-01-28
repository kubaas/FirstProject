# 🚀 INSTRUKCJA DEPLOYMENT - BACKEND + FRONTEND (bez karty)

**Wszystko darmowe, bez karty kredytowej!**

## ✅ CO MASZ:

- ✅ Kod na GitHub: https://github.com/kubaas/FirstProject
- ✅ GitHub Actions workflow gotowy
- ✅ Frontend skonfigurowany do Replit URL
- ✅ Backend skonfigurowany do deployment'u

---

## 📝 KROKI (5 minut):

### KROK 1: Włącz GitHub Pages (2 minuty)

```bash
# Terminal - to już jest pushowane, teraz konfiguracja na GitHub

# 1. Przejdź na https://github.com/kubaas/FirstProject
# 2. Kliknij: Settings (guzik u góry)
# 3. W menu po lewej: Pages
# 4. "Build and deployment" sekcja:
#    - Source: Deploy from a branch
#    - Branch: main
#    - Folder: / (root)
# 5. Kliknij: Save

# Frontend będzie dostępny za ~1 minutę na:
# https://kubaas.github.io/FirstProject/
```

### KROK 2: Utwórz Backend na Replit (3 minuty)

```bash
# 1. Przejdź na https://replit.com
# 2. Kliknij: Sign up → GitHub (zaloguj się)
# 3. Kliknij: + Create
# 4. Wybierz: Import from GitHub
# 5. Wklej: https://github.com/kubaas/FirstProject
# 6. Czekaj ~2 minuty (setup)
# 7. Kliknij: Run (guzik u góry)

# Backend będzie dostępny na:
# https://FirstProject.kubaas.replit.dev/api/
```

---

## 🎉 GOTOWE!

Po ~3 minutach masz:

```
FRONTEND:  https://kubaas.github.io/FirstProject/
BACKEND:   https://FirstProject.kubaas.replit.dev/api/
```

Oba są połączone automatycznie!

---

## ✅ WERYFIKACJA:

1. **Frontend** - otwórz https://kubaas.github.io/FirstProject/
   - Powinna się załadować aplikacja
   - Gradient background + przycisk onboarding

2. **Backend** - otwórz https://FirstProject.kubaas.replit.dev/api/health
   - Powinno wyświetlić: `{"status":"ok"}`

3. **Test flow**:
   - W frontend: wpisz dane w formularz
   - Powinno się wyświetlić menu z planem posiłków

---

## ⚠️ WAŻNE RZECZY:

### GitHub Pages
- Automatycznie rebuildo wuje się na każdy push do `main`
- Jest delay ~1 minuta przed nową wersją
- Zawsze latest wersja kodu

### Replit Backend
- Restartuje się raz dziennie (darmowy plan)
- Dane znikają po restarcie (baza H2 w pamięci)
- Po restarcie czeka ~30 sekund na startup
- Chcesz trwałą bazę? Mogę dodać SQLite

---

## 🔄 JAK AKTUALIZOWAĆ KOD:

### Frontend:
```bash
cd /Users/jakubsmus/Programing/FirstProject
git add .
git commit -m "Moje zmiany"
git push origin main
```
GitHub Pages się automatycznie przebuduje (czekaj ~1 minutę).

### Backend:
```bash
git push origin main
```
Replit się automatycznie restartuje (~30 sekund).

---

## 💰 KOSZT:

- GitHub Pages: **0 zł** (zawsze darmowe)
- Replit: **0 zł** (zawsze darmowe)
- Domena: **0 zł** (GitHub Pages subdomena)

**RAZEM: 0 zł/miesiąc**

---

## 🆘 PROBLEM?

1. **Frontend nie ładuje się** → sprawdź czy GitHub Pages jest włączony
2. **Backend nie działa** → sprawdź czy Replit pokazuje status "Running"
3. **Frontend nie łączy się z backend'em** → sprawdź DevTools (F12 → Console) czy są błędy

---

**Powodzenia! 🚀**
