function getData(folder, class, saveFile)

files = dir([folder '/*.ppm']);
for file = files'
    I = imgaussfilt(histeq(rgb2gray(imread([folder '/' file.name]))),2);
    
    corners = detectHarrisFeatures(I);

    imshow(I); hold on;
    data = corners.selectStrongest(30);
    plot(data);
    truesize([500 500]);

    data = data.Location;
    data = round(data);
    
    s = ginput(2);
    close

    targetCorners = data(s(1,1) < data(:, 1) & data(:,1) < s(2,1) & s(1,2) < data(:, 2) & data(:,2) < s(2,2), :);
    targetCorners = reshape(targetCorners', 1, []);
    targetCorners = [targetCorners class];
    
    dlmwrite(saveFile,targetCorners,'-append');
end