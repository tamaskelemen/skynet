import React, { Component } from 'react';
import WorldWind from '@nasaworldwind/worldwind';

class Map extends Component {

  constructor(props) {
    super(props)
    this.state = {
      companyData: []
    }
  }

  componentDidMount() {
    // Tell WorldWind to log only warnings and errors.
    WorldWind.Logger.setLoggingLevel(WorldWind.Logger.LEVEL_WARNING);

    // Create the WorldWindow.
    var wwd = new WorldWind.WorldWindow('canvasOne');

    // Create and add layers to the WorldWindow.
    var layers = [
      // Imagery layers.
      { layer: new WorldWind.BMNGLayer(), enabled: true },
      { layer: new WorldWind.BMNGLandsatLayer(), enabled: false },
      { layer: new WorldWind.BingAerialLayer(null), enabled: false },
      { layer: new WorldWind.BingAerialWithLabelsLayer(null), enabled: true },
      { layer: new WorldWind.BingRoadsLayer(null), enabled: false },
      { layer: new WorldWind.OpenStreetMapImageLayer(null), enabled: false },
      // Add atmosphere layer on top of all base layers.
      { layer: new WorldWind.AtmosphereLayer(), enabled: true },
      // WorldWindow UI layers.
      { layer: new WorldWind.CompassLayer(), enabled: true },
      { layer: new WorldWind.CoordinatesDisplayLayer(wwd), enabled: true },
      { layer: new WorldWind.ViewControlsLayer(wwd), enabled: true },
    ];

    for (var l = 0; l < layers.length; l++) {
      layers[l].layer.enabled = layers[l].enabled;
      wwd.addLayer(layers[l].layer);
    }

    fetch('http://localhost:8080/api/connection/get')
      .then(response => response.json())
      .then(response => this.setState({
        companyData: response
      }));
  }

  render() {
    return (
      <div>
        <canvas id="canvasOne" width="1000" height="1000">
          Your browser does not support HTML5 Canvas.
        </canvas>
      </div>
    );
  }
}

export default Map;