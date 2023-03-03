

/**
 * @author tcirksen
 * */
/** Verbindung mit websocket für den datenempfang aus der postgresql db */
var socket = new WebSocket("ws://localhost:8080/woche");
socket.onmessage = function (event) {
    var message = JSON.parse(event.data);
    // .TYPE hat die message im Websocket erhalten
    if(message.type === "wochenEintrag_created"){
        createKalenderEintragByWebsocket(message.kalenderEintragId, message.datum, message.name,message.startTime,message.endTime, message.eintragsArt);
    } else if(message.type === "wochenEintrag_updated"){
        updateKalenderEintragByWebsocket(message.kalenderEintragId, message.datum, message.name,message.startTime,message.endTime, message.eintragsArt);
    }else if(message.type === "wochenEintrag_deleted"){
        console.log("========================");
        var event = calendar.getEventById(message.id);
        console.log(event);
        event.remove();
    }
};

/**
 * @author tcirksen
 * */
/** Dieser Block dient zur aktualisierung des Modals, der user soll beim Kochen
 * zB keinen Gästeinput angezeigt bekommen. */
var dropdown = document.getElementById("dropdown-field");
var inputFieldName = document.getElementById("input-field-eintrag-name");
var inputFieldGuest = document.getElementById("input-field-eintrag-guest");
var divDisplayGuest = document.getElementById("div-display-guest");

dropdown.addEventListener("change", function() {
    if (dropdown.value === "KOCHEN") {
        inputFieldName.style.display = "block";
        divDisplayGuest.style.display ="none";

    } else if (dropdown.value === "BESUCH") {
        inputFieldName.style.display = "block";
        divDisplayGuest.style.display ="block";
    } else if (dropdown.value === "EINKAUFEN"){
        inputFieldName.style.display = "block";
        divDisplayGuest.style.display ="none";
    } else if (dropdown.value === "GENERELL"){
        inputFieldName.style.display = "block";
        divDisplayGuest.style.display ="block";
    }
});

/** Modal wird initial geladen */
modalEintraege();

/**
 * @author tcirksen
 * */
/** Methode wird ausgeführt sobald auf sumbit im modal geklickt wird
 *  es werden sich erst  alle inputs geholt, dann in ein OBJECT geschrieben.
 *  Dieses OBJECT ist ein POSTKEintragDTO und hat dieselben variablen namen.
 *  Anschließend wird ein einfacher fetch mit der Resource gemacht.
 *  Das OBJECT wird stringify't und übersendet an die Schnittstelle*/
function addWochenEintrag(){

    const artInput = document.getElementById("dropdown-field");
    const bezeichnungInput = document.getElementById("input-field-eintrag-name");
    const datumInput = document.getElementById("input-field-eintrag-date");
    const startTimeInput = document.getElementById("input-field-eintrag-time-start");
    const endTimeInput = document.getElementById("input-field-eintrag-time-end");
    const anzahlGaesteInput = document.getElementById("input-field-eintrag-guest");
    const beteiligteInput = document.getElementById("input-field-eintrag-beteiligte");
    const beschreibungInput = document.getElementById("input-field-eintrag-beschreibung");

    const form = document.getElementById('modal-form');
    if(!form.checkValidity()) {
        form.reportValidity();
    } else{
        var obj = new Object();
        const artInputValue = artInput.value.toUpperCase();
        const beteiligte = splitStringByComma(beteiligteInput.value);

        obj.name = bezeichnungInput.value;
        obj.datum = datumInput.value;
        obj.startTime = startTimeInput.value;
        obj.endTime = endTimeInput.value;
        obj.eintragsArt = artInputValue;
        console.log(beteiligte);
        obj.beteiligte = beteiligte;
        console.log(anzahlGaesteInput.value);
        obj.anzahlGaeste = anzahlGaesteInput.value;
        obj.beschreibung = beschreibungInput.value;

        console.log(obj);
        console.log(JSON.stringify(obj));

        fetch("/wgverwaltung/kalender", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(obj)
        }).then(function (response) {
            // popups erstellen, die dem user feedback geben
            if (response.ok) {
                console.log("SUCCESSFUL PATCH!!!");
            } else {
                alert("Fehler bei erstellen des Elements " + response.status);
            }
        })
            .catch(function (error) {
                alert("Fehler bei erstellen des Elements: " + error);
            });
    }

}
/**
 * @author tcirksen
 * */
/** https://www.w3schools.com/jsref/jsref_split.asp
 *  Methode dient dazu das der String in eine List geschrieben werden kann
 *  "Tim, Maxi"
 *  List: {
 *      Tim,
 *      Maxi
 *  }*/
function splitStringByComma(str) {
    return str.split(",").map(function(item) {
        return item.trim();
    });
}

/**
 * @author tcirksen
 * */
/** Methode die per Button klick das Modal öffnet, welches funktionalitäten wie speichern etc. bietet */
function modalEintraege() {
    var modal = document.getElementById("modal-woche-eintrag");
    console.log(modal);

    //Der button der das Modal öffnen soll wird gegettet
    var btn = document.getElementById("modal-open");
    console.log(btn);

    //Wenn submit gedrückt wird soll die methode ausgeführt werden
    var submitButton = document.getElementById("submit-eintrag");
    submitButton.addEventListener("click", function () {
        addWochenEintrag();
    })
    // Elemente die für das Schließen des Modal zuständig sind
    var span = document.getElementById("close-x-modal");
    var close = document.getElementById("close-modal");

    //Wenn der Button gedrück wird, wird das modal angezeigt

    btn.onclick = function () {
        openAddKalenderEintragModal();
    }
    //Hier wird das Modal geschlossen
    span.onclick = function () {
        modal.style.display = "none";
    }
    close.onclick = function () {
        modal.style.display = "none";
    }
    var background = document.getElementById("modalbackground")
    // Per klick ausserhalb des Windows wird das modal geschlossen, fühlt sich besser an
    background.onclick = function (event) {
        if (event.target === modalbackground) {
            console.log("fasdf");
            modal.style.display = "none";
        }
    }
    // Form element wird gegettet
    var form = document.getElementById("modal-form");
}
/**
 * @author tcirksen
 * */
/**
 * Diese Methode öffnet das Modal zum hinzufügen von Kalendereinträgen und räumt alte werte auf
 * */
function openAddKalenderEintragModal(){

    var deleteBtn = document.getElementById('delete-kalenderEintrag')
    deleteBtn.style.display = "none";

    var modal = document.getElementById('modal-woche-eintrag');

    document.getElementById('dropdown-field').value = "KOCHEN";
    document.getElementById('input-field-eintrag-name').value = "";
    document.getElementById('input-field-eintrag-date').value = "";
    document.getElementById('input-field-eintrag-time-start').value = "";
    document.getElementById('input-field-eintrag-time-end').value = "";
    document.getElementById('input-field-eintrag-guest').value = 0;
    document.getElementById('input-field-eintrag-beteiligte').value = "";
    document.getElementById('input-field-eintrag-beschreibung').value = "";

    var modalTitle = document.getElementById('modal-title');
    modalTitle.innerText = 'Einen Eintrag hinzufügen';
    var el = document.getElementById('submit-eintrag'),
        elClone = el.cloneNode(true);

    el.parentNode.replaceChild(elClone, el);
    elClone.addEventListener("click", (e) => {
        addWochenEintrag();
    });

    modal.style.display = "block";
    //Für den autofocus, damit direkt geschrieben werden kann
    document.getElementById("input-field-eintrag-name").focus();
}

/**
 * @author tcirksen
 * */
/** Methode die ein Kalendereintrag live erzeugt per Websocket */
function createKalenderEintragByWebsocket(id,date, title, start, end, eventArt){
    var color = '#ffffff';
    if(eventArt === "KOCHEN"){
        color = '#18570f';
    }else if(eventArt === "BESUCH"){
        color = '#cc0b0b';
    }else if(eventArt === "EINKAUFEN"){
        color = '#ffe607';
    }else if(eventArt === "GENERELL"){
        color = '#002aff';
    }
    console.log("Das ist die Farbe: " + color);
    var event = new Object();

    event.id = id;
    event.title = title;
    event.start = date+"T"+start;
    event.end = date+"T"+end;
    event.color = color;

    console.log(event);

    calendar.addEvent(event);
    //$('#calendar').fullCalendar( 'renderEvent', event, true);
}

/**
 * @author tcirksen
 * */
/** Methode die ein Kalendereintrag live updatet per Websocket */
function updateKalenderEintragByWebsocket(id,date, title, start, end, eventArt){
    var color = '#ffffff';
    if(eventArt === "KOCHEN"){
        color = '#18570f';
    }else if(eventArt === "BESUCH"){
        color = '#cc0b0b';
    }else if(eventArt === "EINKAUFEN"){
        color = '#ffe607';
    }else if(eventArt === "GENERELL"){
        color = '#002aff';
    }
    console.log("Das ist die Farbe: " + color);
    var event = calendar.getEventById(id);

    event.setProp('title',title);
    event.setStart(date+"T"+start);
    event.setEnd(date+"T"+end);
    event.setProp('color',color);

    console.log(event);


    //calendar.addEvent(event);
    //$('#calendar').fullCalendar( 'renderEvent', event, true);
}
/** Methode die per Input-type und einem button zur dieser KW springt
 * @author tcirksen
 * */
function kwUndJahrAktualisieren(){

    var kw = document.getElementById("kw_input").value;
    var jahr = document.getElementById("jahr_input").value;

    if(kw < 1 || kw > 53){
        kw = 1;
    }
    if(jahr < 1970 || jahr > 3000){
        jahr = 2023;
    }
    window.location.href = "http://localhost:8080/woche/"+jahr+"/"+kw;
}
/**
 * @author tcirksen
 * */
function deleteKalenderEintrag(eventId){

    if (confirm("Willst du den Kalendereintrag wirklich löschen?")) {
        var request = new Request("http://localhost:8080/wgverwaltung/kalender/" + eventId, {
            method: "DELETE",
            headers: {"Content-Type": "application/json"}
        });
        // mit fetch zu quarkus markus senden
        fetch(request)
            .then(function (response) {
                // popups erstellen, die dem user feedback geben
                if (response.ok) {
                    console.log("Der Kalendereintrag wurde deleted")

                } else {
                    alert("Fehler beim löschen des Kalendereintrags " + response.status);
                }
            })
            .catch(function (error) {
                alert("Fehler beim löschen des Kalendereintrags: " + error);
            });
    }
}
/**
 * @author majaesch
 * */
function openEditKalenderEintragModal(eventId) {
    var modal = document.getElementById('modal-woche-eintrag');
    // When the user clicks the button, open the modal

    var modalTitle = document.getElementById('modal-title');
    modalTitle.innerText = 'Eintrag bearbeiten';

    var deleteBtn = document.getElementById('delete-kalenderEintrag');
    deleteBtn.style.display = "block";

    deleteBtn.addEventListener("click", (e) => {

        deleteKalenderEintrag(eventId);
    });
    /** Hier wird <b>GEGETTET</b>
     *  */
    fetch("/wgverwaltung/kalender/" + eventId, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(response => response.json())
        .then(data => {
            console.log("openEditEventModal - get request:");
            console.log(data);
            document.getElementById('dropdown-field').value = data.eintragsArt;
            document.getElementById('input-field-eintrag-name').value = data.name;
            document.getElementById('input-field-eintrag-date').value = data.datum;
            document.getElementById('input-field-eintrag-time-start').value = data.startTime;
            document.getElementById('input-field-eintrag-time-end').value = data.endTime;
            document.getElementById('input-field-eintrag-guest').value = data.anzahlGaeste;
            var beteiligteString = '';
            for(let i=0; i<data.beteiligte.length; i++){
                beteiligteString += data.beteiligte[i];
                if(i !== data.beteiligte.length-1){
                    beteiligteString += ', ';
                }
            }
            document.getElementById('input-field-eintrag-beteiligte').value = beteiligteString;
            document.getElementById('input-field-eintrag-beschreibung').value = data.beschreibung;
        })
        .catch(error => {
            console.log(error)
        }).then(okay => {modal.style.display = "block";}); //auf die fetch warten
    //Mehrere Events mit einem Button
    var el = document.getElementById('submit-eintrag'),
        elClone = el.cloneNode(true);

    el.parentNode.replaceChild(elClone, el);

    /** Hier wird <b>geputted</b> */
    console.log("Das ist die PUT id " + eventId);
    elClone.addEventListener("click", (e) => {

        putWochenEintrag(eventId);
    });
}

/**
 * @author majaesch
 * */
function putWochenEintrag(eventId){
    const artInput = document.getElementById("dropdown-field");
    const bezeichnungInput = document.getElementById("input-field-eintrag-name");
    const datumInput = document.getElementById("input-field-eintrag-date");
    const startTimeInput = document.getElementById("input-field-eintrag-time-start");
    const endTimeInput = document.getElementById("input-field-eintrag-time-end");
    const anzahlGaesteInput = document.getElementById("input-field-eintrag-guest");
    const beteiligteInput = document.getElementById("input-field-eintrag-beteiligte");
    const beschreibungInput = document.getElementById("input-field-eintrag-beschreibung");

    var obj = new Object();
    const artInputValue = artInput.value.toUpperCase();
    const beteiligte = splitStringByComma(beteiligteInput.value);

    obj.name = bezeichnungInput.value;
    obj.datum = datumInput.value;
    obj.startTime = startTimeInput.value;
    obj.endTime = endTimeInput.value;
    obj.eintragsArt = artInputValue;
    console.log(beteiligte);
    obj.beteiligte = beteiligte;
    console.log(anzahlGaesteInput.value);

    obj.anzahlGaeste = anzahlGaesteInput.value;

    obj.beschreibung = beschreibungInput.value;


    console.log(obj);
    console.log(JSON.stringify(obj));

    fetch("/wgverwaltung/kalender/" + eventId, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(obj)
    }).then(function (response) {
        // popups erstellen, die dem user feedback geben
        if (response.ok) {
            console.log("SUCCESSFUL PUT!!!");
        } else {
            alert("Fehler bei erstellen des Elements " + response.status);
        }
    })
        .catch(function (error) {
            alert("Fehler bei erstellen des Elements: " + error);
        });
}