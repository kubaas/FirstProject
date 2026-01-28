# 🚀 Instrukcja Deploymentu na Render.com (DARMOWE)

## 📋 Wymagania
- Konto GitHub (darmowe)
- Konto Render.com (darmowe, https://render.com)
- Konto Neon.tech (darmowe, https://neon.tech)

---

## 1️⃣ Przygotowanie kodu

```bash
# Upewnij się że jesteś w folderze projektu
cd /Users/jakubsmus/Programing/FirstProject

# Zainicjalizuj Git (jeśli nie masz już repo)
git init
git add .
git commit -m "Initial commit"

# Dodaj origin do GitHub
git remote add origin https://github.com/TWOJA_NAZWA_UZYTKOWNIKA/FirstProject.git
git branch -M main
git push -u origin main
```

---

## 2️⃣ Utwórz bazę danych na Neon.tech

1. Przejdź na https://neon.tech
2. Zaloguj się lub utwórz konto (za GitHub)
3. Kliknij "New Project"
4. Wybierz Region (np. Europe/Skandynawia)
5. Utwórz bazę danych
6. Skopiuj connection string:
   ```
   postgresql://user:password@host/database
   ```
7. Zachowaj ten string - będzie potrzebny w kroku 4

---

## 3️⃣ Utwórz usługi na Render.com

### A. Backend

1. Przejdź na https://render.com i zaloguj się (GitHub)
2. Kliknij "New +" → "Web Service"
3. Połącz swoje GitHub repo
4. Wpisz nazwę: `imo-backend`
5. Ustawienia:
   - **Build Command**: `mvn clean package -DskipTests -q`
   - **Start Command**: `java -jar target/imo-backend-*.jar`
   - **Runtime**: Docker
6. Kliknij "Create Web Service"
7. Czekaj na build (5-10 minut)

### B. Dodaj zmienne środowiskowe do backendu

W panelu Render (strona backendu):
1. Idź do "Environment"
2. Dodaj zmienne:
   ```
   PORT=8080
   SPRING_DATASOURCE_URL=postgresql://user:password@host/database
   SPRING_DATASOURCE_USERNAME=your_user
   SPRING_DATASOURCE_PASSWORD=your_password
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   ```
   (Użyj connection string z Neon)

3. Kliknij "Save"
4. Service się przebuduje automatycznie

### C. Frontend

1. Kliknij "New +" → "Web Service"
2. Połącz GitHub
3. Nazwa: `imo-frontend`
4. Ustawienia:
   - **Build Command**: `npm install && npm run build`
   - **Start Command**: `nginx -g 'daemon off;'`
   - **Runtime**: Docker
5. Kliknij "Create Web Service"
6. Czekaj na build

---

## 4️⃣ Konfiguracja CORS w backendu

Edytuj `backend/src/main/java/com/example/imo/config/WebConfig.java`:

```java
package com.example.imo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/api/**")
        .allowedOriginPatterns("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
```

Push do GitHub:
```bash
git add .
git commit -m "Add CORS config"
git push
```

Backend się przebuduje automatycznie.

---

## 5️⃣ Zmień URL backendu w frontend

Edytuj `frontend/src/app/api.service.ts`:

```typescript
private getBaseUrl(): string {
  const hostname = window.location.hostname;
  
  if (hostname === 'localhost' || hostname === '127.0.0.1') {
    return 'http://localhost:8080/api';
  }
  
  // Production URL z Render
  return 'https://imo-backend.onrender.com/api';
}
```

Push:
```bash
git add .
git commit -m "Update backend URL for production"
git push
```

---

## 6️⃣ Uruchomienie

1. Backend powinien być dostępny: `https://imo-backend.onrender.com/api/health`
2. Frontend powinien być dostępny: `https://imo-frontend.onrender.com`
3. Kliknij link do frontendu i testuj!

---

## ⚠️ Ważne Informacje

### Ograniczenia darmowego tier Render:
- ✅ Frontend i Backend działają 24/7
- ⚠️ Backend usypia się po 15 minutach bez aktywności (wakening takes ~30 sec)
- ✅ Baza danych na Neon jest w pełni darmowa
- ⚠️ Render ma shared resources (nie najszybsze, ale do testów OK)

### Rozwiązania ograniczeń:
1. **Aby backend nie usnął**: Dodaj ping co 10 minut (w Angular):
```typescript
setInterval(() => {
  this.http.get('https://imo-backend.onrender.com/api/health').subscribe();
}, 10 * 60 * 1000);
```

2. **Aby mieć pełną moc**: Przejdź na płatne plany (Render $7/miesiąc lub Oracle Cloud Free Tier)

---

## 🎉 Gotowe!

Twoja aplikacja jest online. Wyślij link do `imo-frontend.onrender.com` komuś i może z niej korzystać!

Pytania? Napisz! 🚀
