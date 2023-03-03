package com.wg.boundary.rest.payloads;

/**
 * @author majaesch
 *
 * */
public interface ExamplePayloads {
    String EXAMPLE_KALENDER_EINTRAG = """
            {
              "name": "Zu netto",
              "datum": "2023-02-12",
              "startTime": "13:45:30.123456789",
              "endTime": "13:45:30.123456789",
              "eintragsArt": "EINKAUFEN",
              "beteiligte": [
                "timbo",
                "max"
              ],
              "anzahlGaeste": 1,
              "beschreibung": "wir brauchen was zu futtern"
            }""";
    String EXAMPLE_KALENDER_EINTRAG_2 = """
            {
              "name": "Zu aldi",
              "datum": "2023-02-10",
              "startTime": "13:45:30.123456789",
              "endTime": "13:45:30.123456789",
              "eintragsArt": "EINKAUFEN",
              "beteiligte": [
                "timbo",
                "max"
              ],
              "anzahlGaeste": 1,
              "beschreibung": "wir brauchen was zu futtern"
            }""";
    String EXAMPLE_KALENDER_EINTRAG_3 = """
            {
              "name": "Zu rewe",
              "datum": "2023-02-05",
              "startTime": "13:45:30.123456789",
              "endTime": "13:45:30.123456789",
              "eintragsArt": "EINKAUFEN",
              "beteiligte": [
                "timbo",
                "max"
              ],
              "anzahlGaeste": 1,
              "beschreibung": "wir brauchen was zu futtern"
            }""";
    String EXAMPLE_KALENDER_EINTRAG_4 = """
            {
              "name": "Zu edika",
              "datum": "2023-02-06",
              "startTime": "13:45:30.123456789",
              "endTime": "13:45:30.123456789",
              "eintragsArt": "EINKAUFEN",
              "beteiligte": [
                "timbo",
                "max"
              ],
              "anzahlGaeste": 1,
              "beschreibung": "wir brauchen was zu futtern"
            }""";
}
