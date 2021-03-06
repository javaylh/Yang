<template>
  <div id="container_id" :class="'login-container ' + 'login-container-back' + randomNumber">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" autocomplete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">
          {{ $t('login.title') }}
        </h3>
        <lang-select class="set-language" />
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          :placeholder="$t('login.username')"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

      <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
        <el-form-item prop="password">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            :key="passwordType"
            ref="password"
            v-model="loginForm.password"
            :type="passwordType"
            :placeholder="$t('login.password')"
            name="password"
            tabindex="2"
            autocomplete="on"
            @keyup.native="checkCapslock"
            @blur="capsTooltip = false"
            @keyup.enter.native="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
      </el-tooltip>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">
        {{ $t('login.logIn') }}
      </el-button>

      <div style="position:relative">
        <div class="tips">
          <span>{{ $t('login.username') }} : admin</span>
          <span>{{ $t('login.password') }} : {{ $t('login.any') }}</span>
        </div>
        <div class="tips">
          <span style="margin-right:18px;">
            {{ $t('login.username') }} : editor
          </span>
          <span>{{ $t('login.password') }} : {{ $t('login.any') }}</span>
        </div>

        <el-button class="thirdparty-button" type="primary" @click="showDialog=true">
          {{ $t('login.thirdparty') }}
        </el-button>
      </div>
    </el-form>

    <el-dialog :title="$t('login.thirdparty')" :visible.sync="showDialog">
      {{ $t('login.thirdpartyTips') }}
      <br>
      <br>
      <br>
      <social-sign />
    </el-dialog>
    <div class="CCoH_windmill">
      <div class="windmill_div">
        <img id="windmill_id" class="windmill_img" src="../../../static/img/windmill.png" @click="changImage()">
      </div>
      <i class="windmill_i" />
    </div>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import LangSelect from '@/components/LangSelect'
import SocialSign from './components/SocialSignin'

export default {
  name: 'Login',
  components: { LangSelect, SocialSign },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'admin',
        password: '111111'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      showDialog: false,
      redirect: undefined,
      otherQuery: {},
      rotate: 0,
      // 随机一个数，用以切换背景图片
      randomNumber: -1
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  created() {
    // window.addEventListener('storage', this.afterQRScan)
  },
  mounted() {
    if (this.loginForm.username === '') {
      this.$refs.username.focus()
    } else if (this.loginForm.password === '') {
      this.$refs.password.focus()
    }
  },
  destroyed() {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
    changImage() {
      // 首先随机生成一个随机数0~10
      this.randomNumber = Math.round(Math.random() * 10)
      // var path = 'url(../../../assets/img/2.jpg)'
      this.rotate = (this.rotate + 90) % 360
      // document.getElementById('windmill_id').style.animation = 'mymove 2s infinite'
      // document.getElementById('windmill_id').style.transform = 'rotate(-' + this.rotate + 'deg)'
      // document.getElementById('container_id').style.backgroundImage = path
    },
    checkCapslock({ shiftKey, key } = {}) {
      if (key && key.length === 1) {
        if (shiftKey && (key >= 'a' && key <= 'z') || !shiftKey && (key >= 'A' && key <= 'Z')) {
          this.capsTooltip = true
        } else {
          this.capsTooltip = false
        }
      }
      if (key === 'CapsLock' && this.capsTooltip === true) {
        this.capsTooltip = false
      }
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
              this.loading = false
              const hour = new Date().getHours()
              var msg = ''
              if (hour < 11) {
                msg = this.$t('login.morning')
              } else if (hour < 14) {
                msg = this.$t('login.noon')
              } else if (hour < 18) {
                msg = this.$t('login.afternoon')
              } else if (hour < 24) {
                msg = this.$t('login.night')
              }
              this.$message({
                showClose: true,
                message: msg + ', ' + this.$t('login.welcomeMsg'),
                type: 'success'
              })
            })
            .catch(() => {
              this.loading = false
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
    // afterQRScan() {
    //   if (e.key === 'x-admin-oauth-code') {
    //     const code = getQueryObject(e.newValue)
    //     const codeMap = {
    //       wechat: 'code',
    //       tencent: 'code'
    //     }
    //     const type = codeMap[this.auth_type]
    //     const codeName = code[type]
    //     if (codeName) {
    //       this.$store.dispatch('LoginByThirdparty', codeName).then(() => {
    //         this.$router.push({ path: this.redirect || '/' })
    //       })
    //     } else {
    //       alert('第三方登录失败')
    //     }
    //   }
    // }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#0f59a4;
$dark_gray:#889aa4;
$light_gray:#eee;
.login-container-back-1 {
  background-image: url('../../../static/img/login.jpg');
}
.login-container-back0 {
  background-image: url('../../../static/img/0.jpg');
}
.login-container-back1 {
  background-image: url('../../../static/img/1.jpg');
}
.login-container-back2 {
  background-image: url('../../../static/img/2.jpg');
}
.login-container-back3 {
  background-image: url('../../../static/img/3.jpg');
}
.login-container-back4 {
  background-image: url('../../../static/img/4.jpg');
}
.login-container-back5 {
  background-image: url('../../../static/img/5.jpg');
}
.login-container-back6 {
  background-image: url('../../../static/img/6.jpg');
}
.login-container-back7 {
  background-image: url('../../../static/img/7.jpg');
}
.login-container-back8 {
  background-image: url('../../../static/img/8.jpg');
}
.login-container-back9 {
  background-image: url('../../../static/img/9.jpg');
}
.login-container-back10 {
  background-image: url('../../../static/img/10.jpg');
}
.login-container {
  min-height: 100%;
  width: 100%;
  // background-color: $bg;
  // background-image: url('../../../static/img/login.jpg');
  opacity: 0.95;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }

    .set-language {
      color: #fff;
      position: absolute;
      top: 3px;
      font-size: 18px;
      right: 0px;
      cursor: pointer;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }

  .CCoH_windmill {
    right: 12px;
    bottom: 0;
    position: absolute;
    -webkit-user-select: none;
    user-select: none;
  }
  .windmill_img {
    display: block;
    width: 100%;
    height: 100%;
    // infinite 无限循环
    animation: que 4s linear;
    // 通过@keyframes规则,能够创建动画 , que 定义动画的名称 可自己定义
    @keyframes que {
    //  以百分比来规定改变发生的时间 也可以通过"from"和"to",等价于0% 和 100%
    0%{-webkit-transform:rotate(-0deg);}
		25%{-webkit-transform:rotate(-90deg);}
		50%{-webkit-transform:rotate(-180deg);}
		75%{-webkit-transform:rotate(-270deg);}
    100%{-webkit-transform:rotate(-360deg);}
    }
  }
  .windmill_img:hover {
    transform: rotate(-666turn);
    transition-delay: 1s;
    transition-property: all;
    transition-duration: 59s;
    transition-timing-function: cubic-bezier(.34,0,.84,1);
  }

  .windmill_div {
    // cursor: pointer; 鼠标移上去变小手
    width: 40px;
    height: 40px;
  }
  .windmill_i {
    margin-top: -6px;
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 5px;
    height: 30px;
    background-color: #fefefe;
  }
}
</style>
