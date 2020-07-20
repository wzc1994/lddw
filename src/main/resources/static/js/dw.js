// **********************************************//
// index 客户端脚本
//
// @author Administrator
// @date 2020-03-08
// **********************************************//

var vm = new Vue({
    el: '#app',
    mixins: [atyTplMixin],
    data: function () {
        return {
            requestBody: '',
            moduleLogs: [],
            loading: false,
            loadingText: '正在执行...',
            userBcInfos: [],
            bcErrorInfo: '',
            bcLoading: false,
            bcButtonText: '开始监控'
        }
    },
    methods: {
        oneKeyClick () {
            let _this = this;
            let message = _this.validate();
            if (message !== "ok") {
                this.warning(message)
                return;
            }
            _this.moduleLogs = [];
            for (let key in moduleInfo) {
                let info = moduleInfo[key];
                this.loadingText = '正在代玩【' + info.name + '】模块...';
                Artery.ajax.post(info.url + this.getParam()).then(function (result) {
                    if (result.success) {
                        _this.moduleLogs.push(result.data);
                    } else {
                        this.warning(result.message)
                    }
                });
            }
        },
        validate () {
            if (!this.requestBody) {
                return "请填写请求体";
            }
            let paramObj = this.requestBodyToObj(this.requestBody);
            if (!paramObj['uid'] || !paramObj['h5token'] || !paramObj['h5openid']) {
                return "请求体中必须含有uid,h5token,h5openid信息";
            }
            return "ok";
        },
        getParam () {
            let paramObj = this.requestBodyToObj(this.requestBody);
            return '?uid=' + paramObj['uid'] + '&h5token=' + paramObj['h5token'] + '&h5openid=' + paramObj['h5openid'];
        },
        requestBodyToObj (paramsStr) {
            let paramObj = {};
            let paramArr = paramsStr.split('&');
            paramArr.forEach(function(paramStr){
                let temp = paramStr.split('=');
                paramObj[temp[0]] = temp[1];
            })
            return paramObj;
        },
        warning (message) {
            this.$message({
                message: message,
                type: 'warning'
            });
        },
        startMonitor () {
            let _this = this;
            let message = _this.validate();
            if (message !== "ok") {
                this.warning(message)
                return;
            }
            Artery.ajax.post('/api/bc/start' + _this.getParam()).then(function (start) {
                if (start.success) {
                    _this.bcLoading = true;
                    _this.bcButtonText = '监控中';
                    _this.userBcInfos = start.data;
                    _this.bcErrorInfo = '';
                    let intervalID = setInterval(function () {
                        Artery.ajax.get('/api/bc/data' + _this.getParam()).then(function (data) {
                            if (data.success) {
                                _this.userBcInfos = data.data.userBcInfos;
                                if (!data.data.timerRun.run) {
                                    _this.bcErrorInfo = data.data.timerRun.msg;
                                    clearInterval(intervalID);
                                    _this.bcLoading = false;
                                    _this.bcButtonText = '开始监控';
                                }
                            } else {
                                _this.bcErrorInfo = data.message;
                                clearInterval(intervalID);
                                _this.bcLoading = false;
                                _this.bcButtonText = '开始监控';
                            }
                        });
                    }, 10 * 1000)
                } else {
                    _this.bcErrorInfo = start.message;
                }
            })
        },
        refreshBcList () {
            let _this = this;
            let message = _this.validate();
            if (message !== "ok") {
                this.warning(message)
                return;
            }
            Artery.ajax.get('/api/bc/data' + _this.getParam()).then(function (data) {
                if (data.success) {
                    _this.userBcInfos = data.data.userBcInfos;
                    _this.bcErrorInfo = '';
                } else {
                    _this.bcErrorInfo = data.message;
                }
            });
        },
        tranlateType (type) {
            switch (type) {
                case '0':
                    return '石头';
                case '1':
                    return '木材';
                case '2':
                    return '铁矿'
                default:
                    return ''
            }
        },
        getTypeClass (type) {
            switch (type) {
                case '0':
                    return 'bc-tran-item shitou';
                case '1':
                    return 'bc-tran-item mucai';
                case '2':
                    return 'bc-tran-item tiekuang'
                default:
                    return ''
            }
        },
        tranlateQuality (quality) {
            switch (quality) {
                case '1':
                    return '白';
                case '2':
                    return '绿';
                case '3':
                    return '蓝'
                case '4':
                    return '紫'
                case '5':
                    return '红'
                default:
                    return ''
            }
        },
        getQualityClass (quality) {
            switch (quality) {
                case '1':
                    return 'bc-tran-item bai';
                case '2':
                    return 'bc-tran-item lv';
                case '3':
                    return 'bc-tran-item lan';
                case '4':
                    return 'bc-tran-item zi';
                case '5':
                    return 'bc-tran-item hong';
                default:
                    return ''
            }
        },
        formatDate (time) {
            return this.dateFormat(time * 1000, 'yyyy-MM-dd HH:mm');
        }
    }
})