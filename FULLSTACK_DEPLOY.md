# Full Stack Deployment: GitHub Pages + Replit

## 🎯 SETUP (wszystko z konsoli, bez karty)

### KROK 1: Włącz GitHub Pages

```bash
# W konsoli, z folderu projektu:
cd /Users/jakubsmus/Programing/FirstProject

# Dodaj workflow do Git i push
git add .github/workflows/deploy.yml
git commit -m "Add GitHub Pages deployment workflow"
git push origin main
```

Teraz:
1. Przejdź na https://github.com/kubaas/FirstProject
2. Ustawienia → Pages
3. "Build and deployment" → Branch: `main` → Folder: `/root`
4. Kliknij Save

Frontend będzie automatycznie dostępny na:
```
https://kubaas.github.io/FirstProject/
```

### KROK 2: Backend na Replit

1. Wejdź na https://replit.com (załóż darmowe konto, GitHub login)
2. Kliknij "+ Create" → "Import from GitHub"
3. Wklej: `https://github.com/kubaas/FirstProject`
4. Czekaj ~2 minuty na setup
5. Kliknij "Run"

Backend będzie dostępny na:
```
https://FirstProject.kubaas.replit.dev/api/
```

### KROK 3: Połącz Frontend z Backend

Edytuj `frontend/src/app/api.service.ts`:

```typescript
private getBaseUrl(): string {
  if (location.hostname === 'localhost' || location.hostname === '127.0.0.1') {
    return 'http://localhost:8080/api';
  }
  // Replit URL
  return 'https://FirstProject.kubaas.replit.dev/api';
}
```

Push:
```bash
git add frontend/src/app/api.service.ts
git commit -m "Update backend URL for Replit"
git push origin main
```

GitHub Pages będzie się automatycznie rebuildo wać.

---

## 🎉 EFEKT:

- **Frontend**: https://kubaas.github.io/FirstProject/
- **Backend**: https://FirstProject.kubaas.replit.dev/api/
- **Koszt**: 0 złotych, bez karty

---

## 💾 Notatki:

- Frontend automatycznie rebuildo wuje się na każdy push do `main` (GitHub Actions)
- Backend na Replit restartuje się raz dziennie (darmowy plan)
- Baza danych: H2 (wbudowana w pamięć) - dane znikają po restarcie, ale na demo OK

Chcesz, żeby dodać trwałą bazę danych? Mogę zrobić SQLite.
