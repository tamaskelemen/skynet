import React, { Component } from 'react';
import Map from '../components/Map';
import Menus from '../components/Menus';
import { API_URL } from '../common/constants';
import chroma from 'chroma-js';
import moment from 'moment';
import _ from 'lodash';

window.moment = moment;
const f = chroma.scale('OrRd')
  .correctLightness()
  .domain([0, 500000]);

class ContractMap extends Component {

  apiData = [];

  state = {
    connections: [],
    companies: [],
    projects: [],
    observations: [],
    animation: {},
    searchValue: '',
  };
  fetchLast = 0;

  setAnimation = animation => this.setState({ animation });

  deriveColor = contract => {
    const [r, g, b] = f((contract && contract.price) || 0).rgb();
    return { r, g, b, a: 1.0 };
  };

  componentDidUpdate(prevProps, prevState, snapshot) {
    if (prevState.animation !== this.state.animation) {
      this.getDataAndRender();
    }
  }

  deriveEdge = location => {
    return {
      lat: parseFloat(location.latitude),
      lon: parseFloat(location.longitude),
    };
  };

  deriveImageSource = company => {
    if (company.name && company.name.match(/NASA|^HQ$/)) {
      return './nasa.png';
    }
    if (company.name && company.name.match(/European Space Agency/)) {
      return './esa.png';
    }
    return './plain-red.png';
  };

  getFilteredContracts = (nodes) => {
    return nodes;
    // const { animation: { rangeStart, rangeEnd } } = this.state;
    // return nodes.filter(node => moment.unix(rangeStart).isBefore(moment(node.contract.endDate)) &&
    //   moment.unix(rangeEnd).isAfter(moment(node.contract.startDate)));
  };

  parseApiData = response => {
    this.apiData = response;
    this.computeConnectionsAndCompanies();
  };

  computeConnectionsAndCompanies = () => {
    const connections = [];
    const companies = [];

    let nodes = this.apiData;

    nodes = nodes.filter(node => this.state.animation.contracts.indexOf(node.name) >= 0);

    while (nodes.length) {
      const node = nodes.pop();

      companies.push({
        ...node,
        location: this.deriveEdge(node.location),
        imageSource: this.deriveImageSource(node),
      });

      if (!node.sub.length) {
        continue;
      }

      const edge1 = this.deriveEdge(node.location);

      this.getFilteredContracts(node.sub).forEach(subNode => {
        const edge2 = this.deriveEdge(subNode.location);
        const { contract } = subNode;
        connections.push({ edge1, edge2, color: this.deriveColor(contract), contract });
        nodes.push(subNode);
      });
    }

    this.setState({
      connections,
      companies,
    });
  };

  componentDidMount() {
    this.getDataAndRender();
  }

  postData = async (url = '', data = {}) => {
    const response = await fetch(
      url, {
        headers: {
          'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(data),
      });
  };

  getData = async (url = '', data = {}) => {
    const queryParam = this.parseQueryParams(data);
    const response = await fetch(url + queryParam, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    return await response.json();
  };

  getDataAndRender = _.debounce(() => {
    const { rangeStart, rangeEnd } = this.state.animation;

    this.setState({ spinner: true });

    this.getData(API_URL + '/contract/get-simple-contracts',
      {
        startDate: moment.unix(rangeStart).format('DD-MM-yyyy'),
        endDate: moment.unix(rangeEnd).format('DD-MM-yyyy'),
      },
    )
      .then(json => {
        this.setState({ spinner: false });
        this.parseApiData(json);
      })
      .catch(() => this.setState({ spinner: false }));
  });

  parseQueryParams = (data = {}) => {
    let queryParam = '?';
    for (const property in data) {
      queryParam += property;
      queryParam += '=';
      queryParam += data[property];
      queryParam += '&';
    }

    return queryParam;
  };

  searchValueChanged = (event) => {
    const project_name = event.project_name;
    this.setState({
      searchValue: project_name,
    });

    if (project_name) {
      this.getData(API_URL + '/company/get', { projectName: this.state.searchValue })
        .then(response => this.setState({ projects: response }));
    }
  };

  render() {
    const { connections, companies, searchValue, projects, observations, animation, spinner } = this.state;
    const { activePage, handlePageChange } = this.props;
    return (
      <Menus animation={animation}
             setAnimation={this.setAnimation}
             activePage={activePage}
             handlePageChange={handlePageChange}
             searchValueChanged={this.searchValueChanged}
             observationsChange={this.observationsChanged}
             searchValue={searchValue}
             spinner={spinner}>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
        <span className="haliho">Discover the connection between space agencies and businesses and what they are about. Choose a Headquarter from the left menu to see how big these networks are.
projects</span>
          <Map connections={connections} companies={companies} project={projects} observations={observations} animation={animation} />
        </div>
      </Menus>
    );
  }
}

export default ContractMap;