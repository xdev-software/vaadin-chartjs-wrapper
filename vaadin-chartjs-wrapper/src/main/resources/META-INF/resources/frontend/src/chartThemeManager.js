if(window.xVaadinChartjsWrapper == undefined) window.xVaadinChartjsWrapper = {};

window.xVaadinChartjsWrapper.tryUpdateChartJsTheming = function tryUpdateChartJsTheming(force) {
  if(!window.xVaadinChartjsWrapper.updatingChartJsThemingRequired && !force) {
    return;
  }

  if(typeof Chart === 'undefined') {
    console.debug("Chart.js is not defined/loaded");
    window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = true;
    return;
  }
  
  console.debug("Updating Chart.js theming");
  try {
    Chart.defaults.color = getComputedStyle(document.documentElement).getPropertyValue("--lumo-body-text-color");
    Chart.defaults.borderColor = getComputedStyle(document.documentElement).getPropertyValue("--lumo-contrast-20pct");

    window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = false;
  } catch(e) {
    window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = true;
    console.debug("Failed to update Chart.js theming", e);
  }
}

window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = true;
