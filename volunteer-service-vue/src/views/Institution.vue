<template>
    <el-table
                highlight-current-row:true
                :data="tableData"
                style="width: 100%"
                @row-click="handleEdit"
                >
            <el-table-column
                    prop="institutionName"
                    label="组织名称"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="admin"
                    label="组织管理员"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="adminId"
                    label="管理员身份证号"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="组织地址"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="tellphone"
                    label="组织电话"
                    align="center"
                    >
            </el-table-column>
            <el-table-column
                    prop="proof"
                    label="凭证"
                    align="center">
                    <template slot-scope="scope">
                            <el-popover placement="right" trigger="click"> <!--trigger属性值：hover、click、focus 和 manual-->
                                <a :href="scope.row.proof" target="_blank" title="查看最大化图片">
                                <img :src="scope.row.proof" style="width: 300px;height: 300px">
                            </a>
                            <img slot="reference" :src="scope.row.proof" style="width: 50px;height: 50px; cursor:pointer">
                        </el-popover>
　　                </template>
            </el-table-column>
            <el-table-column
                    align="right">
                <el-row>
                    <el-button type="primary" @click="agree">通过</el-button>
                </el-row>
                <el-row>
                    <el-button type="danger" @click="reject">驳回</el-button>
                </el-row>
            </el-table-column>
        </el-table>
</template>

<script>
import axios from 'axios';

    export default {
        name : 'Institution',
        mounted(){
            this.select();
        },
        data(){
            return{
                institution:{
                    "institutionName": '',
                    "admin": '',
                    "adminId": '',
                    "tellphone": '',
                    "proof": '',
                    "count": 0,
                    "address": ''
                },
                tableData:[
                    {
                        "institutionName": '0',
                        "admin": '0',
                        "adminId": '0',
                        "tellphone": '0',
                        "proof": 'http://localhost:8090/images/6bfd4e9d9e7d46cd8e6375b1a4b8d0eb.jpg',
                        "address": '0'
                    }
                ]
            }
        },
        methods:{
            handleEdit(row){
                this.institution.institutionName = row.institutionName;
                this.institution.admin = row.admin;
                this.institution.adminId = row.adminId;
                this.institution.tellphone = row.tellphone;
                this.institution.proof = row.proof;
                this.institution.address = row.address;
            },
            select() {
                axios.get("/institution/ratify")
                    .then(resp => {
                    //设置表格数据
                    this.tableData = resp.data.data.list;
                })
            },
            agree(){
                this.$confirm('是否通过该组织申请', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(() => {
                        //console.log("activity:"+this.activity),
                        axios.post("/institution/ratifyOk",this.institution)
                            .then(resp =>{
                                if (resp.data.code === 0) {
                                    this.dialogVisible1 = false;
                                    this.select();
                                    this.$message({
                                        message: '通过成功',
                                        type: 'success'
                                        })
                                } else {
                                    this.$message({
                                        message: '通过失败',
                                        type: 'warning'
                                    })
                                }
                            })
                    }).catch(() => {
                        this.$message({
                        type: 'info',
                        message: '已取消通过'
                    });          
                });
            },
            reject(){
                this.$confirm('是否驳回该组织申请', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(() => {
                        axios.delete("/institution/ratifyFalse",{data:this.institution})
                            .then(resp =>{
                                if (resp.data.code === 0) {
                                    this.dialogVisible1 = false;
                                    this.select();
                                    this.$message({
                                        message: '驳回成功',
                                        type: 'success'
                                    })
                                } else {
                                    this.$message({
                                        message: '驳回失败',
                                        type: 'warning'
                                    })
                                }
                            })
                    }).catch(() => {
                        this.$message({
                        type: 'info',
                        message: '已取消驳回'
                    });          
                });
            }
        }

    }
</script>