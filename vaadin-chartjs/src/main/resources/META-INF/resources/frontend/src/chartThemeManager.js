window.tryUpdateChartJsTheming = function tryUpdateChartJsTheming(force) {
  if(!updatingChartJsThemingRequired && !force) {
    return;
  }

  if(typeof Chart === 'undefined') {
    console.debug("chartJs is not defined/loaded");
    window.updatingChartJsThemingRequired = true;
    return;
  }

  console.debug("Updating chartJs theming");
  try {
    Chart.defaults.color = getComputedStyle(document.documentElement).getPropertyValue("--lumo-body-text-color");
    Chart.defaults.borderColor = getComputedStyle(document.documentElement).getPropertyValue("--lumo-contrast-20pct");

    window.updatingChartJsThemingRequired = false;
  } catch(e) {
    window.updatingChartJsThemingRequired = true;
    console.debug("Failed to update chartJs theming", e);
  }
}

window.updatingChartJsThemingRequired = true;
