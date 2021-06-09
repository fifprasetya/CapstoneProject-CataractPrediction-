# Bangkit 2021 Capstone Project
 
## `Cataract Prediction - B21-CAP0379`
**Machine Learning**
 1. Ibrah Maula Setya Aji - M2242168
 2. Rheditia Ferdiansyah - M2242165

**Mobile Android**

 1. Afif Prasetya - A1841893
 2. Rizky Fajar Nur Pratama  - A0050459

**Cloud Computing**

 1. Ferdinand Leonardi - C0050413

# `Project Brief`
## Machine Learning
## Mobile Android
## Cloud Computing
- Create a GCP Project.
- Enable the APIs we are going to use `Compute Engine and Cloud Storage`
- Create a Cloud Storage bucket
- Create a Compute Engine instance, this will be used to deploy the flask api.

**Deploying flask app into VM Instance**
1. Upload the Flask app and the necessary files into Cloud Storage
2. SSH into the VM Instance
3. Copy the file from Cloud Storage into the VM instance    
```shell
gsutil cp -r gs://bucketname/ .
```
4. Setup a virtual enviroment (In this case I used miniconda)
> You can use these guides: https://docs.conda.io/projects/conda/en/4.6.0/_downloads/52a95608c49671267e40c689e0bc00ca/conda-cheatsheet.pdf
https://uoa-eresearch.github.io/eresearch-cookbook/recipe/2014/11/20/conda/

5. Install the requirments.txt
```python
pip install requirements.txt
```
6. Install PM2 to keep the flask app running at all times
> You can refer to this guide: 
https://www.npmjs.com/package/pm2

7. Run the Flask app
```
pm2 start flaskapi.py
```
