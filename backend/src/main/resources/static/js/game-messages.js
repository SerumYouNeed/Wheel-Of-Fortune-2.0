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

// ----------------------------------------
// WHEEL ANIMATION
// ----------------------------------------

let sectors = [];
let spinningInterval = null;

function initSectors() {
    const wheelSvg = document.querySelector(".wheel svg");
    if (wheelSvg) {
        sectors = Array.from(wheelSvg.querySelectorAll("[data-sector]"));
    }
}

document.addEventListener("DOMContentLoaded", initSectors);

// MAIN LISTENER - only if SWAP .spinning-result
document.body.addEventListener("htmx:afterSwap", (event) => {
    initSectors();

    // check if swap is for .spinning-result
    const swapTarget = event.detail && event.detail.target ? event.detail.target : null;

    // fallback: if event.detail.target is not there, use event.target only
    // if event.target is .spinning-result (rare, chatGPT suggestion)
    const fallbackTarget = event.target && event.target.classList && event.target.classList.contains('spinning-result')
        ? event.target
        : null;

    const actualTarget = swapTarget || fallbackTarget;

    // if swap is not for container (important with HX-Retarget)
    if (!actualTarget || !actualTarget.classList || !actualTarget.classList.contains('spinning-result')) {
        return; // ===> STOP
    }

    const resultBox = actualTarget.querySelector("[data-result]");
    if (!resultBox) {
        return; // ===> STOP
    }

    // Parse and start animation
    const targetSector = parseInt(resultBox.dataset.result, 10);
    if (!Number.isFinite(targetSector)) return;

    animateWheelTo(targetSector);
});

// ----------------------------------------
// ANIMATION - FAST FAZE
// ----------------------------------------
function animateWheelTo(targetSector) {
    if (!sectors.length) return;

    let index = 0;
    let cycles = 0;
    const maxCycles = 8;

    clearInterval(spinningInterval);

    spinningInterval = setInterval(() => {

        // cleaning effects
        sectors.forEach(s => {
            s.style.fillOpacity = "1";
            s.style.filter = "none";
            s.style.stroke = "";
            s.style.strokeWidth = "";

        });

    // lightning sector
    sectors[index].style.fillOpacity = "0.5";
    sectors[index].style.filter = "drop-shadow(0 0 30px gold)";
    sectors[index].style.stroke = "gold";
    sectors[index].style.strokeWidth = "4";


    index = (index + 1) % sectors.length;

    if (index === 0) cycles++;

    if (cycles >= maxCycles) {
        clearInterval(spinningInterval);
        slowDownToTarget(targetSector);
    }

    }, 80);
}

// ----------------------------------------
// ANIMATION - SLOW FAZE AND STOP
// ----------------------------------------
function slowDownToTarget(targetSector) {
    if (!sectors.length) return;

    let index = 0;
    let delay = 150;

    // cleaning effects
    const step = () => {
        sectors.forEach(s => {
                    s.style.fillOpacity = "1";
                    s.style.filter = "none";
                    s.style.stroke = "";
                    s.style.strokeWidth = "";

                });

    // lightning sector
    sectors[index].style.fillOpacity = "0.5";
    sectors[index].style.filter = "drop-shadow(0 0 30px gold)";
    sectors[index].style.stroke = "gold";
    sectors[index].style.strokeWidth = "4";


    // If target sector than stop
    if (parseInt(sectors[index].dataset.sector) === targetSector) {
        return;
    }

    index = (index + 1) % sectors.length;
    delay += 45;

    setTimeout(step, delay);
    };

    step();
}

// Spin enable during animation
function setSpinEnabled(enabled) {
    const btn = document.querySelector(".spin-btn");
    if (!btn) return;
    btn.disabled = !enabled;
}
