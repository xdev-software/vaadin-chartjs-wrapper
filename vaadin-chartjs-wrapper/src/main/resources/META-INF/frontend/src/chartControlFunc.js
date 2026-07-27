if(window.xVaadinChartjsWrapper == undefined) window.xVaadinChartjsWrapper = {};

/** https://stackoverflow.com/a/46522991 */
if(!window.xVaadinChartjsWrapper.dataStorage) {
  window.xVaadinChartjsWrapper.dataStorage = {
    _storage: new WeakMap(), /* This is important to not cause a memory leak */
    put: function (element, key, obj) {
        if (!this._storage.has(element)) {
            this._storage.set(element, new Map());
        }
        this._storage.get(element).set(key, obj);
    },
    get: function (element, key) {
        return this._storage.get(element).get(key);
    },
    has: function (element, key) {
        return this._storage.has(element) && this._storage.get(element).has(key);
    },
    remove: function (element, key) {
        var ret = this._storage.get(element).delete(key);
        if (!this._storage.get(element).size === 0) {
            this._storage.delete(element);
        }
        return ret;
    }
  }
}

window.xVaadinChartjsWrapper.buildChart = function buildChart(parentId, canvasId, jsonpayload) {
  console.debug("buildChart: running");
  window.xVaadinChartjsWrapper.tryDestroyChart(canvasId);
  
  let parent = document.getElementById(parentId);
  if (parent == null) {
    console.log("buildChart: parent was null!");
    return;
  }
  
  while (parent.firstChild) {
    parent.removeChild(parent.firstChild);
  }
  
  let canvas = document.createElement("canvas");
  canvas.id = canvasId;

  parent.appendChild(canvas);

  let ctx = canvas.getContext('2d');

  let chart = new Chart(ctx, jsonpayload);

  // Add chart to canvas
  window.xVaadinChartjsWrapper.dataStorage.put(document.getElementById(canvasId), 'chart-data', chart);
}

window.xVaadinChartjsWrapper.tryDestroyChart = function tryDestroyChart(canvasId) {
  let canvas = document.getElementById(canvasId);
  if(canvas == null) {
    console.debug(`tryDestroyChart: Canvas[id=${canvasId}] was null!`);
    return;
  }
  
  let chart = window.xVaadinChartjsWrapper.dataStorage.get(canvas, 'chart-data');
  
  if(chart == null) {
      console.debug(`tryDestroyChart: Chart in Canvas[id=${canvasId}] was null!`);
      return;
  }
  
  chart.destroy();
  console.debug(`tryDestroyChart: Destroying chart in Canvas[id=${canvasId}]!`);
}


try {
  window.xVaadinChartjsWrapper.tryUpdateChartJsTheming();
} catch {
  // Theming might not be available
}
