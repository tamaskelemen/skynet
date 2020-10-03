import React, { Component } from 'react';
import Map from '../components/Map';
import Sidebar from '../components/Sidebar';
import { Layout } from 'antd';

const { Sider } = Layout;

class MainMap extends Component {
  render() {
    return (
      <Layout>
        <Sider width={300} theme="light">
          <Sidebar />
        </Sider>
        <Layout>
          <Map />
        </Layout>
      </Layout>
    );
  }
}

export default MainMap;