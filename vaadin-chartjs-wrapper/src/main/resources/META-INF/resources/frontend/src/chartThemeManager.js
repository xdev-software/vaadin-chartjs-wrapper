if(window.xVaadinChartjsWrapper == undefined) window.xVaadinChartjsWrapper = {};

window.xVaadinChartjsWrapper.tryUpdateChartJsTheming = function tryUpdateChartJsTheming(force) {
  if(!updatingChartJsThemingRequired && !force) {
    return;
  }

  if(typeof Chart === 'undefined') {
    console.debug("chartJs is not defined/loaded");
    window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = true;
    return;
  }

  console.debug("Updating chartJs theming");
  try {
    Chart.defaults.color = getComputedStyle(document.documentElement).getPropertyValue("--lumo-body-text-color");
    Chart.defaults.borderColor = getComputedStyle(document.documentElement).getPropertyValue("--lumo-contrast-20pct");

    window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = false;
  } catch(e) {
    window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = true;
    console.debug("Failed to update chartJs theming", e);
  }
}

window.xVaadinChartjsWrapper.updatingChartJsThemingRequired = true;
