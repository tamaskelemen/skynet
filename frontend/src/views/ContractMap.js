import React, { Component } from 'react';
import Map from '../components/Map';
import Menus from '../components/Menus';
import { API_URL } from '../common/constants';
import chroma from 'chroma-js';

const f = chroma.scale(['black','red','yellow','white'])
  .correctLightness()
  .domain([0,100000]);

class ContractMap extends Component {
  state = {
    connections: [],
    companies: [],
    projects: [],
    searchValue: ''
  };

  deriveColor = contract => {
    const [r, g, b] = f((contract && contract.price) || 0).rgb();
    return { r, g, b, a: 1.0 };
  };

  deriveEdge = location => {
    return {
      lat: parseFloat(location.latitude),
      lon: parseFloat(location.longitude),
    };
  };

  deriveImageSource = company => {
    if (Math.random() > 0.5) {
      return './nasa.png';
    }
    return './plain-red.png'
  }

  parseApiData = response => {
    const connections = [];
    const companies = [];

    const nodes = response;

    while (nodes.length) {
      const node = nodes.pop();

      companies.push({
        ...node,
        location: this.deriveEdge(node.location),
        imageSource: this.deriveImageSource(node)
      });

      if (!node.sub.length) {
        continue;
      }

      const edge1 = this.deriveEdge(node.location);

      node.sub.forEach(subNode => {
        const edge2 = this.deriveEdge(subNode.location);
        connections.push({ edge1, edge2, color: this.deriveColor(node.contract) });
        nodes.push(subNode);
      });
    }

    this.setState({
      connections,
      companies,
    });
  };

  componentDidMount() {
    fetch(API_URL + '/connection/get')
      .then(response => response.json())
      .then(this.parseApiData);
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
  }

  parseQueryParams = (data = {}) => {
    let queryParam = '?';
    for ( const property in data) {
      queryParam += property;
      queryParam += '=';
      queryParam += data[property];
      queryParam += '&';
    }

    return queryParam;
  }

  searchValueChanged = (event) => {
    const project_name = event.project_name
    this.setState({
      searchValue: project_name
    });

    if (project_name) {
      this.getData( 'http://localhost:8080/api/company/get', { projectName: this.state.searchValue})
      // this.getData(API_URL + '/company/get', { projectName: this.state.searchValue})
        .then(response => this.setState({projects: response}));
    }
  }


  render() {
    const { connections, companies, searchValue , projects} = this.state;
    const { activePage, handlePageChange } = this.props;
    return (
      <Menus activePage={activePage} handlePageChange={handlePageChange} searchValueChanged={this.searchValueChanged} searchValue={searchValue}>
        <Map connections={connections} companies={companies} project={projects}/>
      </Menus>
    );
  }
}

export default ContractMap;