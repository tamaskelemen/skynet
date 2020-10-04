import React, { Component } from 'react';
import { Checkbox, Form, Slider } from 'antd';
import moment from 'moment';

const min = moment('01-01-1980').unix();
const max = moment('01-01-2020').unix();
const marks = {
  [min]: moment.unix(min).year(),
  // 26: '26°C',
  // 37: '37°C',
  [max]: moment.unix(max).year(),
};

const rangeSizeMin = 30 * 24 * 60 * 60;
const rangeSizeDefault = 4000 * 24 * 60 * 60;
const rangeSizeMax = 10000 * 24 * 60 * 60;
const animationSpeedMin = 30 * 24 * 60 * 60;
const animationSpeedDefault = 120 * 24 * 60 * 60;
const animationSpeedMax = 360 * 24 * 60 * 60;

window.moment = moment;

class Index extends Component {

  interval = undefined;

  animation = {
    enabled: false,
    rangeStart: min,
    rangeEnd: undefined,
    rangeSize: rangeSizeDefault,
    animationSpeed: animationSpeedDefault,
  };

  constructor(props) {
    super();
    props.setAnimation(this.animation);
  }

  componentDidUpdate(prevProps, prevState, snapshot) {
    const { enabled } = this.props.animation;
    if (enabled !== prevProps.animation.enabled) {
      if (enabled) {
        this.startAnimation();
      } else {
        this.endAnimation();
      }
    }
  }

  setNextRange = () => {
    let { rangeStart, rangeEnd, rangeSize, animationSpeed } = this.props.animation;

    if (moment.unix(rangeEnd).isSameOrAfter(moment.unix(max))) {
      rangeStart = min;
    }

    this.props.setAnimation({
      ...this.props.animation,
      rangeStart: rangeStart + animationSpeed,
      rangeEnd: rangeStart + animationSpeed + rangeSize,
    });
  };

  startAnimation = () => {
    this.interval = setInterval(() => {
      this.setNextRange();
    }, 100);
  };

  endAnimation = () => clearInterval(this.interval);

  // componentDidMount() {
  //   // animate
  // }

  tipFormatter = value => moment.unix(value).format('MM-YYYY');

  handleMainSliderChange = values => this.props.setAnimation({ ...this.props.animation, rangeStart: values[0], rangeEnd: values[1] });

  handleStartToggle = ({ target: { checked } }) => this.props.setAnimation({ ...this.props.animation, enabled: checked });

  handleRangeChange = rangeSize => this.props.setAnimation({ ...this.props.animation, rangeSize });

  handleAnimationSpeedChange = animationSpeed => this.props.setAnimation({ ...this.props.animation, animationSpeed });

  render() {
    const { animation } = this.props;
    const { rangeStart, rangeEnd, rangeSize, enabled, animationSpeed } = animation || {};
    return (
      <div style={{ marginTop: 16, marginLeft: -22 }}>
        <Form>
          <Form.Item>
            <Checkbox checked={enabled} onChange={this.handleStartToggle}>Animate</Checkbox>
          </Form.Item>
          <Form.Item style={{ padding: '0 16px' }}>
            <Slider disabled={enabled} range className="main-slider" min={min} max={max} value={[rangeStart, rangeEnd]} marks={marks}
                    tipFormatter={this.tipFormatter}
                    onChange={this.handleMainSliderChange} />
          </Form.Item>

          <Form.Item label="Range">
            <Slider disabled={!enabled} min={rangeSizeMin} max={rangeSizeMax} value={rangeSize} onChange={this.handleRangeChange} />
          </Form.Item>

          <Form.Item label="Speed">
            <Slider disabled={!enabled} min={animationSpeedMin} max={animationSpeedMax} value={animationSpeed}
                    onChange={this.handleAnimationSpeedChange} />
          </Form.Item>
        </Form>
      </div>
    );
  }
}

Index.defaultProps = {
  animation: {},
  setAnimation: () => {}
}

export default Index;