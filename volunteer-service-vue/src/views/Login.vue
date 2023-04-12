<template>
<div class="container">
    <div class="box">
        <el-form :model="form">
                <div class="p">
                    <h1> LOGIN IN </h1>
                </div>
            <el-form-item prop="admin">
                    <el-input v-model="form.admin" placeholder="请输入账号"></el-input>
                </el-form-item>
            <el-form-item prop="password">
                    <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
                <div class="btn">
                     <el-button type="primary" @click="onSubmit">登录</el-button>
                </div>

                <div class="bt">
                  <el-link type="danger" @click="reset">没有账号？前去注册</el-link>
                </div>
            </el-form-item>
        </el-form>
    </div>

    <el-dialog
      title="注册"
      :visible.sync="dialogVisible"
      width="35%"
      >
      <el-form ref="resform" :model="resfrom" status-icon :rules="rules" label-width="80px">
          <el-form-item label="账号" prop="admin">  
              <el-input v-model="resfrom.admin"></el-input>            
          </el-form-item>
          <el-form-item label="密码"  prop="password">            
              <el-input type = "password" v-model="resfrom.password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">            
             <el-input type="password" v-model="resfrom.checkPass" ></el-input>
          </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save('resform')">注 册</el-button>
      </span>
    </el-dialog>
</div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    var checkadmin = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('账号不能为空'));
        }else{
          callback();
        }
      };
    var password1 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          callback();
        }
      };
      var password2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.resfrom.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return {
      form: {
        admin: '',
        password: ''
      },
      dialogVisible: false,
      resfrom: {
        admin: '',
        password: '',
        checkPass: ''
      },
          rules: {
          password: [
            { validator: password1, trigger: 'blur' }
          ],
          checkPass: [
            { validator: password2, trigger: 'blur' }
          ],
          admin: [
            { validator: checkadmin, trigger: 'blur' }
          ]
        }
    }
  },
  methods: {
    onSubmit () {
      this.axios.post('/root/login', this.form).then(res => res.data).then(res => {
        console.log(res)
        if (res.code === 0) {
          this.$message({
            type: 'success',
            message: '登陆成功！'
          })
          console.log(res.data)
          localStorage.setItem('admin', JSON.stringify(res.data.admin))
          localStorage.setItem('password', JSON.stringify(res.data.password))
          // 为了更新导航栏
          this.$router.push("/home")
          
        } else {
          this.$message({
            type: 'error',
            message: '输入有误请重新输入!'
          })
        }
      })
    },
    reset(){
      this.dialogVisible = true
      this.$nextTick(() => {
      this.resetForm()
      })
    },
      resetForm () {
      this.$refs.resform.resetFields()
    },
      save(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.toSave()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

    toSave () {
      this.axios.post('/root/register', this.resfrom).then(res => {
        if (res.data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success'
          })
          this.dialogVisible = false
        }
      })
    }
  }
}
</script>

<style scoped>
@import '../assets/login.css';
</style>
