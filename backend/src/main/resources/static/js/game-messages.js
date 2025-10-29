document.body.addEventListener("htmx:afterOnLoad", function (evt) {
  const target = evt.detail.elt.closest(".message");
  if (target) {
    target.classList.add("visible");

    setTimeout(() => {
      target.classList.remove("visible");
      target.innerHTML = "";
    }, 3000);
  }
  console.log("htmx:afterOnLoad event fired!");
});

document.body.addEventListener("htmx:afterSwap", (e) => {
  console.log("✅ HTMX swapped:", e.detail.target);
});


//document.body.addEventListener("htmx:afterOnLoad", (event) => {
//  console.log("htmx:afterOnLoad event fired!", event.detail.xhr.responseText);
//});
