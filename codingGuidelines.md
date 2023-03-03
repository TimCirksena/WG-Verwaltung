# Coding Guidelines für das SWA Projekt

## Templates
Alle Templates werden mit dem Namen "namexy_view.html" benannt.

## Variablen
Variablen sollten immer im CamelCase-Format benannt werden.

## DTOs
Alle DTOs sollten public sein.

## Websockets
Websockets sollten mit dem gleichen Namen wie die zugehörige View benannt werden.
Zum Beispiel: "namexy_view" wird zu "NamexyWebsocket".

## Ressourcen
Ressourcen sollten ebenfalls mit dem gleichen Namen wie die zugehörige View benannt werden.
Zum Beispiel: "namexy_view" wird zu "NamexyResource".

## Klassennamen
Klassennamen sollten immer großgeschrieben werden.

## Git Commits
Nachfolgend sind die empfohlenen Nachrichten für Git Commits aufgeführt:
- `feat:` für neue Funktionalitäten
- `tidy:` für gelöschte oder kommentierte Code-Abschnitte
- `fix:` für behobene Probleme oder Bugs

## Git-Workflow
- Am Ende des Arbeitstages wurde mindestens der neue Stand committed, sodass Maxi oder Tim weiterarbeiten können
- Pull Request werden nicht benötigt

## Framework
Das zu verwendende Framework ist Bulma.