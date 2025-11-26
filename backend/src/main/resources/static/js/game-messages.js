document.body.addEventListener("htmx:afterSwap", function (evt) {
  const target = evt.target.closest(".message");
  if (target) {
    // show (fade in)
    target.classList.add("visible");

    setTimeout(() => {
      target.classList.remove("visible");

      // after transition (0.5s) remove text
      setTimeout(() => {
        target.innerHTML = "";
      }, 500);
    }, 3000);
  }
});

document.body.addEventListener("htmx:afterSwap", (event) => {
if (event.target.matches(".puzzle")) {
  htmx.ajax('GET', '/game/balance', {target: '#player-balance'});
}
});

let sectors = [];
let spinningInterval = null;

// Po KAŻDYM swapie HTMX – próbujemy odczytać wynik i uruchomić animację
document.body.addEventListener("htmx:afterSwap", (event) => {

    // 1. Pobierz SVG koła (zawsze istnieje)
    const wheelSvg = document.querySelector(".wheel svg");
    if (wheelSvg) {
        sectors = Array.from(wheelSvg.querySelectorAll("[data-sector]"));
    }

    // 2. Pobierz miejsce, gdzie HTMX wkłada wynik
    const resultContainer = document.querySelector(".spinning-result");
    if (!resultContainer) return;

    // 3. Znajdź element z `data-result`
    const resultBox = resultContainer.querySelector("[data-result]");
    if (!resultBox) return;

    const targetSector = parseInt(resultBox.dataset.result);
    if (!targetSector) return;

    // 4. Uruchom animację
    animateWheelTo(targetSector);
});


// ----------------------------------------
// ANIMACJA – FAZA SZYBKA
// ----------------------------------------
function animateWheelTo(targetSector) {
    if (!sectors.length) return;

    let index = 0;
    let cycles = 0;
    const maxCycles = 8;

    clearInterval(spinningInterval);

    spinningInterval = setInterval(() => {

        sectors.forEach(s => s.style.fillOpacity = "1");
        sectors[index].style.fillOpacity = "0.5";

        index = (index + 1) % sectors.length;

        if (index === 0) cycles++;

        if (cycles >= maxCycles) {
            clearInterval(spinningInterval);
            slowDownToTarget(targetSector);
        }

    }, 80);
}


// ----------------------------------------
// ANIMACJA – FAZA ZWALNIANIA I ZATRZYMANIE
// ----------------------------------------
function slowDownToTarget(targetSector) {
    if (!sectors.length) return;

    let index = 0;
    let delay = 150;

    const step = () => {
        sectors.forEach(s => s.style.fillOpacity = "1");
        sectors[index].style.fillOpacity = "0.5";

        // Jeżeli trafiliśmy we właściwy sektor → STOP
        if (parseInt(sectors[index].dataset.sector) === targetSector) {
            return;
        }

        index = (index + 1) % sectors.length;
        delay += 45; // stopniowe zwalnianie

        setTimeout(step, delay);
    };

    step();
}
