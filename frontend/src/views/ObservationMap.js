import React, { Component } from 'react';
import { API_URL } from '../common/constants';
import Map from '../components/Map';
import Menus from '../components/Menus';

export default class ObservationMap extends Component {
    constructor(props) {
        super(props);

        this.state = {
            observations: [],
        };
        console.log('observationMap');
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
    
      observationsChanged = (event) => {
        const startDate = event[0].format('yyyy-MM-DD');
        const endDate = event[1].format('yyyy-MM-DD');
    
        this.getData(API_URL + '/observation/getAll', { startDate, endDate})
          .then(response => this.setState({observations: response}));
      }

    render() {
        const { observations } = this.state;
        const { activePage, handlePageChange } = this.props;
        return (
          <Menus activePage={activePage} handlePageChange={handlePageChange} observationsChange={this.observationsChanged}>
            <Map observations={observations} />
          </Menus>
        );
    }
}