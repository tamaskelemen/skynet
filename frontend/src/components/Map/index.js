import React, { Component } from 'react';
import WorldWind from '@nasaworldwind/worldwind';
import { Popover } from 'antd';

class Map extends Component {
  wwd = undefined;
  highlightedItems = [];
  mouseX;
  mouseY;

  constructor(props) {
    super(props);
    this.state = {
      highlightedItems: [],
      width: 1000,
      height: 1000,
    };
  }

  componentDidUpdate(prevProps, prevState, snapshot) {
    const { connections, companies } = this.props;
    if (prevProps.connections !== connections) {
      this.drawConnections(connections);
    }
    if (prevProps.companies !== companies) {
      this.drawCompanies(companies);
    }
  }

  drawConnections = lines => {
    const { wwd } = this;

    // Add the path to a layer and the layer to the WorldWindow's layer list.
    var pathsLayer = new WorldWind.RenderableLayer();
    pathsLayer.displayName = 'Paths';

    lines.forEach(line => {
      const { edge1, edge2, color } = line;

      var pathPositions = [];
      pathPositions.push(new WorldWind.Position(edge1.lat, edge1.lon, 1e5));
      pathPositions.push(new WorldWind.Position(edge2.lat, edge2.lon, 1e5));
      // Create the path.
      var path = new WorldWind.Path(pathPositions, null);
      path.altitudeMode = WorldWind.GREAT_CIRCLE; // The path's altitude stays relative to the terrain's altitude.
      // path.followTerrain = true;
      path.extrude = true; // Make it a curtain.
      // path.useSurfaceShapeFor2D = true; // Use a surface shape in 2D mode.

      // Create and assign the path's attributes.
      var pathAttributes = new WorldWind.ShapeAttributes(null);
      pathAttributes.outlineColor = new WorldWind.Color(color.r, color.g, color.b, color.a);
      pathAttributes.interiorColor = new WorldWind.Color(color.r, color.g, color.b, color.a);
      pathAttributes.drawVerticals = path.extrude; //Draw verticals only when extruding.
      path.attributes = pathAttributes;

      // Create and assign the path's highlight attributes.
      var highlightAttributes = new WorldWind.ShapeAttributes(pathAttributes);
      highlightAttributes.outlineColor = WorldWind.Color.RED;
      highlightAttributes.interiorColor = new WorldWind.Color(1, 1, 1, 0.5);
      path.highlightAttributes = highlightAttributes;

      // Add the path to a layer and the layer to the WorldWindow's layer list.
      pathsLayer.addRenderable(path);
    });

    wwd.addLayer(pathsLayer);
  };

  drawCompanies = companies => {
    const { wwd } = this;

    var placemark,
      placemarkAttributes = new WorldWind.PlacemarkAttributes(null),
      highlightAttributes,
      placemarkLayer = new WorldWind.RenderableLayer('Placemarks');

    // Set up the common placemark attributes.
    placemarkAttributes.imageScale = 2;
    placemarkAttributes.imageOffset = new WorldWind.Offset(
      WorldWind.OFFSET_FRACTION, 0.3,
      WorldWind.OFFSET_FRACTION, 0.0);
    placemarkAttributes.imageColor = WorldWind.Color.WHITE;
    placemarkAttributes.labelAttributes.offset = new WorldWind.Offset(
      WorldWind.OFFSET_FRACTION, 0.5,
      WorldWind.OFFSET_FRACTION, 1.0);
    placemarkAttributes.labelAttributes.color = WorldWind.Color.YELLOW;
    placemarkAttributes.drawLeaderLine = true;
    placemarkAttributes.leaderLineAttributes.outlineColor = WorldWind.Color.RED;

    // For each placemark image, create a placemark with a label.
    companies.forEach(company => {
      const { lat, lon } = company.location;

      // Create the placemark and its label.
      placemark = new WorldWind.Placemark(new WorldWind.Position(lat, lon, 1e5), true, null);
      placemark.label = company;
      placemark.altitudeMode = WorldWind.RELATIVE_TO_GROUND;

      // Create the placemark attributes for this placemark. Note that the attributes differ only by their
      // image URL.
      placemarkAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);
      placemarkAttributes.imageSource = './plain-red.png';
      placemark.attributes = placemarkAttributes;

      // Create the highlight attributes for this placemark. Note that the normal attributes are specified as
      // the default highlight attributes so that all properties are identical except the image scale. You could
      // instead vary the color, image, or other property to control the highlight representation.
      highlightAttributes = new WorldWind.PlacemarkAttributes(placemarkAttributes);
      highlightAttributes.imageScale = 2.2;
      placemark.highlightAttributes = highlightAttributes;

      // Add the placemark to the layer.
      placemarkLayer.addRenderable(placemark);
    });

    // Add the placemarks layer to the WorldWindow's layer list.
    wwd.addLayer(placemarkLayer);
  };

  // The common pick-handling function.
  handlePick = o => {
    const { wwd } = this;
    let { highlightedItems } = this.state;

    // The input argument is either an Event or a TapRecognizer. Both have the same properties for determining
    // the mouse or tap location.
    var x = o.clientX,
      y = o.clientY;

    var redrawRequired = highlightedItems.length > 0; // must redraw if we de-highlight previously picked items

    // De-highlight any previously highlighted placemarks.
    for (var h = 0; h < highlightedItems.length; h++) {
      highlightedItems[h].highlighted = false;
    }
    highlightedItems = [];

    // Perform the pick. Must first convert from window coordinates to canvas coordinates, which are
    // relative to the upper left corner of the canvas rather than the upper left corner of the page.
    var pickList = wwd.pick(wwd.canvasCoordinates(x, y));
    if (pickList.objects.length > 0) {
      redrawRequired = true;
    }

    // Highlight the items picked by simply setting their highlight flag to true.
    if (pickList.objects.length > 0) {
      for (var p = 0; p < pickList.objects.length; p++) {
        pickList.objects[p].userObject.highlighted = true;

        // Keep track of highlighted items in order to de-highlight them later.
        highlightedItems.push(pickList.objects[p].userObject);

        // Detect whether the placemark's label was picked. If so, the "labelPicked" property is true.
        // If instead the user picked the placemark's image, the "labelPicked" property is false.
        // Applications might use this information to determine whether the user wants to edit the label
        // or is merely picking the placemark as a whole.
        if (pickList.objects[p].labelPicked) {
          console.log('Label picked');
        }
      }
    }

    // Update the window if we changed anything.
    if (redrawRequired) {
      wwd.redraw(); // redraw to make the highlighting changes take effect on the screen
    }

    this.setState({ highlightedItems });
  };

  handleMouseMove = (event) => {
    var eventDoc, doc, body;

    event = event || window.event; // IE-ism

    // If pageX/Y aren't available and clientX/Y are,
    // calculate pageX/Y - logic taken from jQuery.
    // (This is to support old IE)
    if (event.pageX == null && event.clientX != null) {
      eventDoc = (event.target && event.target.ownerDocument) || document;
      doc = eventDoc.documentElement;
      body = eventDoc.body;

      event.pageX = event.clientX +
        (doc && doc.scrollLeft || body && body.scrollLeft || 0) -
        (doc && doc.clientLeft || body && body.clientLeft || 0);
      event.pageY = event.clientY +
        (doc && doc.scrollTop || body && body.scrollTop || 0) -
        (doc && doc.clientTop || body && body.clientTop || 0);
    }

    this.mouseX = event.pageX;
    this.mouseY = event.pageY;
    // Use event.pageX / event.pageY here
  };

  componentDidMount() {
    // Tell WorldWind to log only warnings and errors.
    WorldWind.Logger.setLoggingLevel(WorldWind.Logger.LEVEL_WARNING);

    // Create the WorldWindow.
    var wwd = this.wwd = new WorldWind.WorldWindow('canvasOne');

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
      { layer: new WorldWind.CompassLayer(), enabled: false },
      { layer: new WorldWind.CoordinatesDisplayLayer(wwd), enabled: true },
      { layer: new WorldWind.ViewControlsLayer(wwd), enabled: false },
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
      if (startPoint.objects.length === 1 && startPoint.objects[0].isTerrain) {
        startPointPosition = { latitude: startPoint.objects[0].position.latitude, longitude: startPoint.objects[0].position.longitude };
      }

      let endPointPosition;
      const xEndPoint = 1000;
      const yEndPoint = 1000;
      const endPoint = wwd.pick(wwd.canvasCoordinates(xEndPoint, yEndPoint));

      if (endPoint.objects.length === 1 && endPoint.objects[0].isTerrain) {
        endPointPosition = { latitude: endPoint.objects[0].position.latitude, longitude: endPoint.objects[0].position.longitude };
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
    // Listen for mouse moves and highlight the placemarks that the cursor rolls over.
    wwd.addEventListener('mousemove', this.handlePick);

    // Listen for taps on mobile devices and highlight the placemarks that the user taps.
    var tapRecognizer = new WorldWind.TapRecognizer(wwd, this.handlePick);

    document.onmousemove = this.handleMouseMove;
  }

    return response.json();
  componentWillUnmount() {
    this.wwd.removeEventListener('mousemove', this.handlePick);
    document.onmousemove = undefined;
  }

  showTooltip = highlightedItems =>
    !!highlightedItems.find(item => item.layer && item.layer.displayName === 'Placemarks');
  deriveTooltipContent = highlightedItems => {
    const company = highlightedItems.find(item => item.layer && item.layer.displayName === 'Placemarks')
    return JSON.stringify(company && company.label.name);
  }

  render() {
    const { width, height, highlightedItems } = this.state;
    console.log(highlightedItems, this.showTooltip(highlightedItems), this.deriveTooltipContent(highlightedItems))
    return (
      <div>
        <Popover content={<span>{this.deriveTooltipContent(highlightedItems)}</span>} title="Title" visible={this.showTooltip(highlightedItems)}>
          <div style={{ position: 'absolute', top: this.mouseY, left: this.mouseX }} />
        </Popover>,
        <canvas id="canvasOne" width="1000" height="1000" style={{ backgroundColor: 'black' }}>
          Your browser does not support HTML5 Canvas.
        </canvas>
      </div>
    );
  }
}

export default Map;