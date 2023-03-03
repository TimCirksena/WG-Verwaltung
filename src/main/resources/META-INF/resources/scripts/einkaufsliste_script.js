/**
 * @author tcirksen
 * */

/** Modal wird initial geladen */
modalAddEintrag();


/** Verbindung mit websocket für den datenempfang aus der postgresql db */
var socket = new WebSocket("ws://localhost:8080/einkaufsliste");
socket.onmessage = function (event) {
    console.log("HALLLO");
    var message = JSON.parse(event.data);
    console.log(message);
    // .TYPE hat die message im Websocket erhalten
    if(message.type === "eintrag_created"){
        createEintragByWebsocket(message.id,message.menge,message.item,message.besteller);
    }
    if(message.type === "eintrag_deleted"){
        console.log(message.id);
        document.getElementById("eintragbox"+message.id).remove();
    }
};

/**
 * @author tcirksen
 * */
/** Methode die per Button klick das Modal öffnet, welches funktionalitäten wie speichern etc. bietet */
function modalAddEintrag() {
    var modal = document.getElementById("modal-example");
    console.log(modal);

    //Der button der das Modal öffnen soll wird gegettet
    var btn = document.getElementById("modal-open");
    console.log(btn);

    //Wenn submit gedrückt wird soll die methode ausgeführt werden
    var submitButton = document.getElementById("submit-eintrag");
    submitButton.addEventListener("click", function () {
        addEintrag();
    })
    // Elemente die für das Schließen des Modal zuständig sind
    var span = document.getElementById("close-x-modal");
    var close = document.getElementById("close-modal");

    //Wenn der Button gedrück wird, wird das modal angezeigt
    btn.onclick = function () {
        modal.style.display = "block";
        //Für den autofocus, damit instant geschrieben werden kann
        document.getElementById("input-field-item").focus();
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
            modal.style.display = "none";
        }
    }
    // Form element wird gegettet
    var form = document.getElementById("modal-form");
}

/**
 * @author tcirksen
 * */
/** Request zum deleten eines Eintrags, die ID wird  in der Html per quarkus.qute übergeben*/
function deleteEintrag(id){

    var obj = new Object();

    obj.eintragId = id;

    var jsonString = JSON.stringify(obj);

    var request = new Request("http://localhost:8080/einkaufsliste", {
        method: "DELETE",
        body: jsonString,
        headers: {"Content-Type": "application/json"}
    });
    // mit fetch zu quarkus markus senden
    fetch(request)
        .then(function (response) {
            // popups erstellen, die dem user feedback geben
            if (response.ok) {
                console.log("Das Element wurde deleted")

            } else {
                alert("Fehler beim löschen des Elements " + response.status);
            }
        })
        .catch(function (error) {
            alert("Fehler beim löschen des Elements: " + error);
        });
}

/**
 * @author tcirksen
 * */
/** Request per fetch um die Inputs an die Schnittstelle zu übertragen */
function addEintrag() {

    const mengeInput = document.getElementById("input-field-menge");
    const itemInput = document.getElementById("input-field-item");
    const bestellerInput = document.getElementById("input-field-besteller");

    const form = document.getElementById('modal-form');
    if(!form.checkValidity()){
        form.reportValidity();
    }else {
        var obj = new Object();

        obj.erledigt = false;
        obj.menge = mengeInput.value;
        obj.item = itemInput.value;
        obj.besteller = bestellerInput.value;

        fetch("/einkaufsliste", {
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
function checkboxValueChange(id){
    var obj = new Object();

    let erledigt = document.getElementById("checkbox-einkauf"+id);

    obj.erledigt = erledigt.checked;
    obj.einkauflistenEintragId = id;


    console.log(obj);
    fetch("/einkaufsliste", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(obj)

    }).then(function (response) {
        // popups erstellen, die dem user feedback geben
        if (response.ok) {
            console.log("SUCCESSFUL PATCH!!!");
        } else {
            alert("Fehler bei änderen des Statuses bei der checkbox " + response.status);
        }
    })
        .catch(function (error) {
            alert("Fehler bei änderen des Statuses bei der checkbox: " + error);
        });
}

/**
 * @author tcirksen
 * */
/** Methode die einen Einkaufseintrag live und initial erzeugt per Websocket */
function createEintragByWebsocket(id, menge, item, besteller){
    let box = document.getElementById("box-of-eintrag");
    let eintragbox = document.createElement("div");
    eintragbox.id = "eintragbox" +id;
    let checkbox = document.createElement("input");
    checkbox.setAttribute("type", "checkbox");
    checkbox.id = "checkbox-einkauf"+id;
    checkbox.addEventListener("click", function() {
        checkboxValueChange(id);
    });

    let p1 = document.createElement("p");
    let p2 = document.createElement("p");
    let p3 = document.createElement("p");

    let deleteButton = document.createElement("button");
    deleteButton.setAttribute("class", "button is-danger");
    deleteButton.innerHTML = "Löschen";
    deleteButton.addEventListener("click", function(event){
        deleteEintrag(id);
    });

    let column1 = document.createElement("div");
    column1.setAttribute("class", "column is-narrow");
    column1.appendChild(checkbox);

    let column2 = document.createElement("div");
    column2.setAttribute("class", "column is-narrow");
    p1.innerHTML=menge;
    column2.appendChild(p1);

    let column3 = document.createElement("div");
    column3.setAttribute("class", "column");
    p2.innerHTML=item;
    column3.appendChild(p2);

    let column4 = document.createElement("div");
    column4.setAttribute("class", "column");
    p3.innerHTML=besteller;
    column4.appendChild(p3);

    let column5 = document.createElement("div");
    column5.setAttribute("class", "column is-narrow");
    column5.appendChild(deleteButton);

    let columns = document.createElement("div");
    box.appendChild(eintragbox);
    eintragbox.appendChild(columns);
    columns.setAttribute("class", "columns is-vcenter");
    columns.appendChild(column1);
    columns.appendChild(column2);
    columns.appendChild(column3);
    columns.appendChild(column4);
    columns.appendChild(column5);

}
