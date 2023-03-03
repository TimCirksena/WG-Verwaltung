# README-SWA

### Projekt kompilieren

1. In PowerShell dieses Kommando zum Kompilieren eingeben

```powershell
./mvnw compile
```

### Projekt testen

1. Docker starten
2. die Tests mit dem unten genannten Kommando in PowerShell starten

```powershell
./mvnw test
```

### Projekt starten

1. Docker starten
2. das Projekt mit dem unten genannten Kommando in PowerShell starten

```powershell
./mvnw quarkus:dev
```

## Web-App verwenden

Der zu verwendende Browser sollte ein moderner Browser wie Firefox oder Chrome sein, und nicht Internet Explorer.

Unter [http://localhost:8080](http://localhost:8080) ist die Landing-Page vorzufinden.

![Login bei bestehender WG](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled.png)

Login bei bestehender WG

![Registrieren bei neuer WG](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%201.png)

Registrieren bei neuer WG

Der Nutzer kann sich unter den bestehenden WGs 

| Nutzername | Passwort |
| --- | --- |
| user | user |
| admin | admin |

anmelden oder einen neue WG registrieren.

Nach dem erfolgreichen anmelden landet der Nutzer im Menü. Hier besteht die Möglichkeit zu verschiedenen Seiten zu navigieren oder sich aus der WG auszuloggen.

![Menü Übersicht](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%202.png)

Menü Übersicht

## Einkaufsliste

In der Einkaufsliste kann der Nutzer Items in seinen Einkaufszettel schreiben. Durch Klicken des Buttons „Einkaufszettel erweitern“ öffnet sich ein Modal.

![Button fürs Erweitern](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%203.png)

Button fürs Erweitern

![Modal öffnet sich nach Betätigung des Buttons](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%204.png)

Modal öffnet sich nach Betätigung des Buttons

Durch den Button „Save changes“ kann der Eintrag zum Einkaufszettel hinzugefügt werden. Die Änderungen sind live! 

![Item wurde hinzugefügt](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%205.png)

Item wurde hinzugefügt

Des Weiteren können Einträge abgehakt und gelöscht werden.

## Wochenübersicht

In der Wochenübersicht kann der Nutzer durch die verschiedenen Kalenderwochen navigieren, sowie neue Einträge einstellen über ein Modal. 

![Öffnet ein Modal](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%206.png)

Öffnet ein Modal

![Eine gewünschte Kalenderwoche auswählen](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%207.png)

Eine gewünschte Kalenderwoche auswählen

![Untitled](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%208.png)

Die Kalenderansicht kann verschiedene Elemente anzeigen, kenntlich durch die verschiedenen Farben. 

Beispielsweise wird ein Eintrag mit der Art: ”Einkaufen” am Mittwoch von 6am - 1pm eingestellt, der Nutzer sieht nachdem bestätigen, direkt diesen Screen:

![Untitled](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%209.png)

### Modal Befüllung Syntax

![Beispiel Eintrag hinzufügen](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2010.png)

Beispiel Eintrag hinzufügen

Es ist darauf zu achten, dass beteiligte Mitbewohner durch ein Komma getrennt werden. 

## Optionen

In den Optionen können WGs umbenannt und gelöscht werden. 

# REST-API verwenden

Für die Verwendung der REST-API wird empfohlen die Swagger UI zu verwenden, da dort die vorgefertigten JSON Dateien nutzbar sind.

### Anmeldung

da Swagger bei aktivierter Form-Authentication keine Möglichkeit bietet sich in der Oberfläche anzumelden, sollte man sich bei der Landing-Page anmelden.

Bei Programmen wie Insomnia ist es auch möglich die vorher genannten Nutzernamen und Passwörter im Authorization Header zu übergeben.

### Interaktion mit der REST-API

Wie bei Swagger üblich, können hier alle unterstützten Requests abgesendet werden.

Bitte verwenden Sie nur diese 3 Endpoints und nicht die -View Endpoints, diese sind für die Web-App gedacht.

![README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2011.png](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2011.png)

![README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2012.png](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2012.png)

Die hier modifizierten Ressourcen sind in der selben WG auch in der Web-App geändert.

### Beispiel einer POST-Anfrage - Kalendereintrag erstellen

Mit klick auf ein Feld kann es geöffnet werden.

![README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2013.png](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2013.png)

Drücken Sie auf diesen Knopf, um fortzufahren

![README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2014.png](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2014.png)

Wählen Sie aus dem Drop-Down Menü ein Beispiel-Objekt aus und modifizieren sie dieses bei Bedarf.

![README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2015.png](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2015.png)

Mit Klick auf den Execute Knopf wird die Anfrage abgeschickt

![README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2016.png](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2016.png)

Bei Erfolg werden die eingegebenen Daten zurückgegeben, mit einem self-link, unter dessen Adresse das Objekt per GET-Anfrage abgerufen werden kann.

![Untitled](README-SWA%20ebdfb8fdb0f3464b91b2840d31d55083/Untitled%2017.png)