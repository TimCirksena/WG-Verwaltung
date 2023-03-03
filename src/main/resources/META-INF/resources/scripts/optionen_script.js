/**
 * gesamte methoden: 
 * @author tcirksen
 * */


/** Coockies werden gelöscht um den User auszuloggen nach änderung des Namen oder löschens */
const LOGGED_COOKIE = "quarkus-credential";
function logout() {
    console.log("logging out")
    //document.cookie = LOGGED_COOKIE + '=; Max-Age=0'
    //https://stackoverflow.com/questions/10593013/delete-cookie-by-name
//falls cookies nicht richtig gelöscht werden
    document.cookie = LOGGED_COOKIE +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    window.location.href = "/";
}

/** Dient dazu, dass diese Funktion nur in Optionen ausgeführt werden soll */
if (location.pathname === "/menu/optionen"){
    modalUpdateNameWG();
}


/** Request zum deleten der WG, zusätzlich ein "confirm" eingebaut zum sichergehen */
function deleteWG(){
        if (confirm("Willst du die WG wirklich löschen?")) {

            const wgNameInput = "Helpful";

            var obj = new Object();

            obj.wgName = wgNameInput;

            var request = new Request("http://localhost:8080/menu", {
                method: "DELETE",
                headers: {"Content-Type": "application/json"},
            });
            // mit fetch zu quarkus markus senden
            fetch(request)
                .then(function (response) {
                    // popups erstellen, die dem user feedback geben
                    if (response.ok) {
                        console.log("Die WG wurde deleted")
                    } else {
                        alert("Fehler beim löschen der WG " + response.status);
                    }
                })
                .catch(function (error) {
                    alert("Fehler beim löschen der Liste: " + error);
                });

        }
}

/** Methode die per Button klick das Modal öffnet, welches funktionalitäten wie speichern etc. bietet */
function modalUpdateNameWG() {
    var modal = document.getElementById("modal-optionenUpdateName");
    console.log(modal);

    //Der button der das Modal öffnen soll wird gegettet
    var btn = document.getElementById("modal-open");
    console.log(btn);

    //Wenn submit gedrückt wird soll die methode ausgeführt werden
    var submitButton = document.getElementById("submit-eintrag");
    submitButton.addEventListener("click", function () {
        updateName();
    })
    // Elemente die für das Schließen des Modal zuständig sind
    var span = document.getElementById("close-x-modal");
    var close = document.getElementById("close-modal");

    //Wenn der Button gedrück wird, wird das modal angezeigt
    btn.onclick = function () {
        modal.style.display = "block";
        //Für den autofocus, damit instant geschrieben werden kann
        document.getElementById("input-field-wgname").focus();
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
/** Request per Fetch für die Namens änderungen der WG */
function updateName() {

    const wgNameInput = document.getElementById("input-field-wgname").value;
    const form = document.getElementById('modal-form');

    if(!form.checkValidity()){
        form.reportValidity();
    } else {
        var obj = new Object();

        obj.wgName = wgNameInput;

        fetch("/menu", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(obj)
        }).then(function (response) {
            if (response.ok) {
                console.log("SUCCESSFUL PATCH!!!");
                logout();
            } else {
                alert("Fehler beim änderen des namens " + response.status);
            }
        })
            .catch(function (error) {
                alert("Fehler beim änderen des namens: " + error);
            });
    }
}
