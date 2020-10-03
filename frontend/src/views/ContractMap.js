import React, { Component } from 'react';
import Map from '../components/Map';
import Menus from '../components/Menus';
import { API_URL } from '../common/constants';

class ContractMap extends Component {
  state = {
    connections: [],
    companies: [],
  };

  deriveColor = contract => {
    return { r: 0, g: 0, b: 1, a: 1.0 };
  };

  deriveEdge = location => {
    return {
      lat: parseFloat(location.latitude),
      lon: parseFloat(location.longitude)
    }
  }

  parseApiData = response => {
    const connections = [];
    const companies = [];

    const nodes = response;

    while (nodes.length) {
      const node = nodes.pop();

      companies.push({
        ...node,
        location: this.deriveEdge(node.location)
      });

      if (!node.sub.length) {
        continue;
      }

      const edge1 = this.deriveEdge(node.location)

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

  render() {
    const { connections, companies } = this.state;
    const { activePage, handlePageChange } = this.props;
    return (
      <Menus activePage={activePage} handlePageChange={handlePageChange}>
        <Map connections={connections} companies={companies} />
      </Menus>
    );
  }
}

export default ContractMap;