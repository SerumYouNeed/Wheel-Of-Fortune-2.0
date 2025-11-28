// --- GLOBAL VARIABLES ---
let sectors = [];
let spinningInterval = null;
let pendingHTMXContent = null;
let onSpinFinished = null;
let isSpinning = false;

// --- INIT SECTORS ---
function initSectors() {
    const wheelSvg = document.querySelector(".wheel svg");
    sectors = wheelSvg ? Array.from(wheelSvg.querySelectorAll("[data-sector]")) : [];
    console.log("[initSectors] found sectors:", sectors.length);
}

document.addEventListener("DOMContentLoaded", initSectors);
document.body.addEventListener("htmx:afterSwap", (ev) => {
    if (ev.target && (ev.target.matches(".wheel") || ev.target.closest(".wheel"))) {
        initSectors();
    }
});

// --- SPIN BUTTON ---
const spinBtn = document.querySelector(".spin-btn");
spinBtn?.addEventListener("click", () => {
    if (isSpinning) return; // nie pozwól na drugi spin

    isSpinning = true;
    const msg = document.querySelector(".spinning-result");
    if (msg) {
        msg.style.display = "none"; // natychmiast ukryj komunikat
        msg.innerHTML = "";         // wyczyść poprzednią treść
    }

    pendingHTMXContent = null;
    onSpinFinished = null;
});

// --- SHOW SPIN MESSAGE ---
function showSpinMessage(htmlContent) {
    const msg = document.querySelector(".spinning-result");
    if (!msg) return;
    const tmp = document.createElement("div");
    tmp.innerHTML = htmlContent;
    const resultBox = tmp.querySelector(".result-box");
    msg.innerHTML = resultBox ? resultBox.innerHTML : htmlContent;
    msg.style.display = "block";
}

// --- HTMX BEFORE SWAP ---
document.body.addEventListener('htmx:beforeSwap', function(event) {
    const isSpinResult = event.detail?.target?.classList?.contains('spinning-result') ||
                         event.target?.classList?.contains('spinning-result');
    if (!isSpinResult) return;

    if (event.detail) event.detail.shouldSwap = false;
    pendingHTMXContent = event.detail?.serverResponse || event.detail?.xhr?.responseText;

    const tmp = document.createElement("div");
    tmp.innerHTML = pendingHTMXContent;
    const resultBox = tmp.querySelector("[data-result]");
    const targetSector = resultBox ? parseInt(resultBox.dataset.result, 10) : null;

    onSpinFinished = () => {
        showSpinMessage(pendingHTMXContent);
        pendingHTMXContent = null;
        onSpinFinished = null;
        isSpinning = false;
    };

    if (Number.isFinite(targetSector)) {
        if (!sectors.length) initSectors();
        animateWheelTo(targetSector);
    } else {
        onSpinFinished(); // fallback
    }
});

// --- WHEEL ANIMATION ---
function animateWheelTo(targetSector) {
    if (!sectors.length) {
        onSpinFinished?.();
        return;
    }

    setSpinEnabled(false);
    let index = 0, cycles = 0, maxCycles = 8;
    clearInterval(spinningInterval);

    spinningInterval = setInterval(() => {
        sectors.forEach(s => { s.style.fillOpacity = "1"; s.style.filter = "none"; s.style.stroke = ""; s.style.strokeWidth = ""; });
        sectors[index].style.fillOpacity = "0.5";
        sectors[index].style.filter = "drop-shadow(0 0 30px gold)";
        sectors[index].style.stroke = "gold";
        sectors[index].style.strokeWidth = "4";

        index = (index + 1) % sectors.length;
        if (index === 0) cycles++;
        if (cycles >= maxCycles) {
            clearInterval(spinningInterval);
            setTimeout(() => slowDownToTarget(targetSector), 120);
        }
    }, 80);
}

function slowDownToTarget(targetSector) {
    if (!sectors.length) {
        onSpinFinished?.();
        return;
    }

    let index = 0, delay = 150;
    const step = () => {
        sectors.forEach(s => { s.style.fillOpacity = "1"; s.style.filter = "none"; s.style.stroke = ""; s.style.strokeWidth = ""; });
        sectors[index].style.fillOpacity = "0.5";
        sectors[index].style.filter = "drop-shadow(0 0 30px gold)";
        sectors[index].style.stroke = "gold";
        sectors[index].style.strokeWidth = "4";

        if (parseInt(sectors[index].dataset.sector, 10) === targetSector) {
            setTimeout(() => { setSpinEnabled(true); onSpinFinished?.(); }, 300);
            return;
        }

        index = (index + 1) % sectors.length;
        delay += 45;
        setTimeout(step, delay);
    };

    step();
}

// --- ENABLE/DISABLE BUTTON ---
function setSpinEnabled(enabled) {
    const btn = document.querySelector(".spin-btn");
    if (btn) btn.disabled = !enabled;
}
