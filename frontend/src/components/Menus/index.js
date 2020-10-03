import React, { Component } from 'react';
import { Button, Layout, Menu } from 'antd';
import { ContainerOutlined, DesktopOutlined, MenuOutlined, PieChartOutlined, SolutionOutlined } from '@ant-design/icons';

const { Sider, Header } = Layout;

class Index extends Component {
  state = {
    collapsed: true,
  };

  handleCollapseClick = () => {
    const { collapsed } = this.state;
    this.setState({ collapsed: !collapsed });
  };

  render() {
    const { children, activePage, handlePageChange } = this.props;
    const { collapsed } = this.state;

    return (
      <Layout>
        <Header className="header">
          <Button ghost className="menu-button" onClick={this.handleCollapseClick}><MenuOutlined /></Button>
          <div className="logo">JagerMilkyWay</div>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={[activePage]}>
            <Menu.Item key="0" onClick={() => handlePageChange('0')}><SolutionOutlined />Space contracts</Menu.Item>
            <Menu.Item key="1" onClick={() => handlePageChange('1')}><PieChartOutlined />Space projects</Menu.Item>
          </Menu>
        </Header>

        <Layout>
          <Sider width={collapsed ? 0 : 300} theme="light">
            <Menu
              defaultSelectedKeys={['1']}
              defaultOpenKeys={['sub1']}
              mode="inline"
              theme="dark"
            >
              <Menu.Item key="1" icon={<PieChartOutlined />}>
                Option 1
              </Menu.Item>
              <Menu.Item key="2" icon={<DesktopOutlined />}>
                Option 2
              </Menu.Item>
              <Menu.Item key="3" icon={<ContainerOutlined />}>
                Option 3
              </Menu.Item>
            </Menu>
          </Sider>

          {children}

        </Layout>
      </Layout>
    );
  }
}

export default Index;