import React, { Component } from 'react';
import WorldWind from '@nasaworldwind/worldwind';
import { API_URL } from '../../common/constants';

class Map extends Component {

  constructor(props) {
    super(props);
    this.state = {
      companyData: [],
      width: 1000,
      height: 1000,
    };
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

    fetch(API_URL + '/connection/get')
      .then(response => response.json())
      .then(response => this.setState({
        companyData: response,
      }));
      let call = true;

    wwd.addEventListener('wheel', (event) => {
      let startPointPosition;
      const xStartPoint = 300;
      const yStartPoint = 300;
      const startPoint = wwd.pick(wwd.canvasCoordinates(xStartPoint, yStartPoint));
      if (startPoint.objects.length === 1 && startPoint.objects[0].isTerrain) {
        startPointPosition = { latitude: startPoint.objects[0].position.latitude, longitude: startPoint.objects[0].position.longitude}
      }

      let endPointPosition;
      const xEndPoint = 1000;
      const yEndPoint = 1000;
      const endPoint = wwd.pick(wwd.canvasCoordinates(xEndPoint, yEndPoint));

      if ( endPoint.objects.length === 1 && endPoint.objects[0].isTerrain) {
        endPointPosition = { latitude: endPoint.objects[0].position.latitude, longitude: endPoint.objects[0].position.longitude}
      }

      if(endPointPosition && startPointPosition && call) {
        call = false;
        this.postData(API_URL + '/connection/get-companies', {
          endPointPosition, startPointPosition
        }).then(result => {
          call = true
        });
      }
    });
  }

  postData = async (url = '', data = {}) => {
    const response = await fetch(
      url, {
        headers: {
          'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(data)
      }
    );

    return response.json();
  }

  render() {
    const { width, height } = this.state;
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