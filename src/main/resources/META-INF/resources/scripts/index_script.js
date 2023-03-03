//Quelle der animation: https://stackoverflow.com/questions/16989585/css-3-slide-in-from-left-transition
/**
 * @author majaesch
 * */
const slider = document.getElementById('slider');

/**
 * oeffnet den Slider mit passender Nachricht und farbe
 * @param message
 * @param isError
 * */
function sliderOpen(message, isError){
    slider.classList.remove('popup');
    slider.innerText = message;
    if(isError){
        slider.classList.remove('is-success')
        slider.classList.add('is-danger');
    } else{
        slider.classList.remove('is-danger');
        slider.classList.add('is-success')
    }
    slider.classList.remove('slide-out')
    slider.classList.add('slide-in');
}
/**
 * schliesst den Slider
 * */
function sliderClose(){
    slider.classList.add('slide-out');
    slider.classList.remove('slide-in');
}


/**
 * sendet eine POST request an den REST endpoint um eine neue WG
 * zu erstellen
 * bei falschen eingaben werden die fehler abgefangen und
 * dem User erklärt
 * */
function registerWG() {
    let wgName = document.getElementById("reg_wgname");
    let password = document.getElementById("reg_wgpassword");

    let obj = {};
    obj.wgName = wgName.value;
    obj.password = password.value;

    console.log(obj);
    wgName.value = "";
    password.value = "";


    fetch("/wgverwaltung", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(obj)
    }).then(function (response) {
        // popups erstellen, die dem user feedback geben
        if (response.ok) {
            //console.log("Nutzer erstellt!");
            sliderOpen('WG erfolgreich registriert, du kannst dich jetzt anmelden!',false);
            setTimeout(() => {
                sliderClose();
            }, 2000);
        } else {

            if(response.status === 422){
                sliderOpen('Registrierung fehlgeschlagen, WG name bereits vergeben!',true);

                setTimeout(() => {
                    sliderClose();
                }, 2000);
            } else if(response.status === 409){
                sliderOpen('Registrierung fehlgeschlagen, wichtige felder sind leer!',true);

                setTimeout(() => {
                    sliderClose();
                }, 2000);
            }
            else {
                sliderOpen('Registrierung fehlgeschlagen, Fehlercode' + response.status,true);

                setTimeout(() => {
                    sliderClose();
                }, 2000);
            }
        }
    })
        .catch(function (error) {
            alert("Fehler bei erstellen des Nutzers: " + error);
        });
}