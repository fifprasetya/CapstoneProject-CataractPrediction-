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
- Preprocess image datasets
- Building and training models with Tensorflow
- Save the model (.h5 extension)
- Use the TFLiteConverter to convert the keras model into tflite model

**Filtering Dataset**
1. Download both dataset in "data_source" folder
2. run Filtering_Data.ipynb to separate the image to training and validation

**Creating Model**
1. Used ImageDataGenerator flow_from_directory to takes the path to a directory & generates batches of augmented data.
2. Run one of the model architecture (baseline & some transfer learning model)
3. Compile & fit the model
4. Optionally you can view the visualization of the accuracy and loss of the model
5. saved the model as .h5 and tflite

## Mobile Android
- Download Android App folder from github
- Open file project on Android studio
- run the apk on the Android Studio Emulator or any Emulator

**Deploying Tflite Model**
- Import this library to the build.gradle
 implementation 'org.tensorflow:tensorflow-lite-support:0.1.0-rc1'
 implementation 'org.tensorflow:tensorflow-lite-metadata:0.1.0-rc1'
- Import Tflite model to the project by clicking the "file" toolbar -> new -> other -> tflite model
- Import label.txt to assets folder by clicking the "file" toolbar -> new -> folder -> assets folder and copy the label.txt to it
- Implement the sample code from the ML assets to the activity/fragment
- Add some new code to get the image data from the file as a bitmap and convert it into byte buffer
- Add the byte buffer to the input model. Example : inputFeature0.loadBuffer(byteBuffer)
- Use the output from the model to get the index value from the label.txt and write that index value to the android UI

**How to Use the App**
- Click the button "Try It Now" at the Home Screen
- Tap the + button to add fundus photographs image
- Tap the predict button
- The app will automatically predict cataract based on the fundus photograph image

Note: The application can only predict cataract from fundus photograph and nothing else
You can get example fundus photograph in this link :
https://drive.google.com/drive/folders/10IJJXyxT2yt1qldTS2SSMhGa-Wm4cYqE?usp=sharing
https://drive.google.com/drive/folders/10IJJXyxT2yt1qldTS2SSMhGa-Wm4cYqE?usp=sharing
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
