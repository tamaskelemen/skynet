import React, { Component } from 'react';
import { API_URL } from '../common/constants';
import Map from '../components/Map';
import Menus from '../components/Menus';

export default class MeteorMap extends Component {
  constructor(props) {
    super(props);

    this.state = {
      meteors: [],
    };
  }

  getData = async (url = '', data = {}) => {
    const queryParam = this.parseQueryParams(data);
    const response = await fetch(url + queryParam, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    return await response.json();
  };

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

  componentDidMount() {
    this.getData(API_URL + '/meteors/all')
      .then(response => this.setState({ meteors: response }));
  }

  meteorChanged = (event) => {
    const startDate = event[0].format('yyyy-MM-DD');
    const endDate = event[1].format('yyyy-MM-DD');

    this.getData(API_URL + '/meteors/all', { startDate, endDate })
      .then(response => this.setState({ meteors: response }));
  };

  render() {
    const { meteors } = this.state;
    const { activePage, handlePageChange } = this.props;
    return (
      <Menus activePage={activePage} handlePageChange={handlePageChange} meteorChanged={this.meteorChanged}>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
        <span className="haliho">Choose a date interval to know in that time where the amateurs reported visible meteors to provide control data for radar observations!</span>
          <Map meteors={meteors} />
        </div>
      </Menus>
    );
  }
}