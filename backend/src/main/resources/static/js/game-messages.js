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

function initSectors() {
    const wheelSvg = document.querySelector(".wheel svg");
    if (wheelSvg) {
        sectors = Array.from(wheelSvg.querySelectorAll("[data-sector]"));
    }
}

document.addEventListener("DOMContentLoaded", initSectors);
document.body.addEventListener("htmx:afterSwap", (event) => {
    initSectors();

    const resultBox = event.detail.target;
    if (resultBox && resultBox.dataset.result) {
        const result = parseInt(resultBox.dataset.result);
        animateWheelTo(result);
    }
});

function animateWheelTo(targetSector) {
    if (!sectors.length) return;

    let index = 0;
    let cycles = 0;
    const maxCycles = 10;

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

function slowDownToTarget(targetSector) {
    if (!sectors.length) return;

    let index = 0;
    let delay = 120;

    function step() {
        sectors.forEach(s => s.style.fillOpacity = "1");
        sectors[index].style.fillOpacity = "0.5";

        if (parseInt(sectors[index].dataset.sector) === targetSector) {
            return; // zatrzymaj się
        }

        index = (index + 1) % sectors.length;
        delay += 40;
        setTimeout(step, delay);
    }

    step();
}